package com.shopman.servlet;

import com.shopman.dao.ImportInvoiceDAO;
import com.shopman.model.ImportInvoice;
import com.shopman.model.Supplier;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/import-invoice")
public class ImportInvoiceServlet extends HttpServlet {
    private ImportInvoiceDAO importInvoiceDAO;

    @Override
    public void init() {
        importInvoiceDAO = new ImportInvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("detail".equals(action)) {
                viewInvoiceDetail(request, response);
            } else {
                viewImportInvoiceList(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/manager/ListImportInvoice.jsp").forward(request, response);
        }
    }

    private void viewImportInvoiceList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        int supplierId = Integer.parseInt(request.getParameter("supplierId"));
        String supplierName = request.getParameter("supplierName");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        List<ImportInvoice> invoices = importInvoiceDAO.getListImportInvoice(supplierId, startDate, endDate);

        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        supplier.setName(supplierName);

        request.setAttribute("supplier", supplier);
        request.setAttribute("listImportInvoice", invoices);
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);

        request.getRequestDispatcher("/manager/ListImportInvoice.jsp").forward(request, response);
    }

    private void viewInvoiceDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
        ImportInvoice invoice = importInvoiceDAO.getImportInvoiceDetail(invoiceId);

        request.setAttribute("importInvoice", invoice);
        request.getRequestDispatcher("/manager/ImportInvoiceDetail.jsp").forward(request, response);
    }
}


package com.shopman.servlet;

import com.shopman.dao.InvoiceDAO;
import com.shopman.model.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO;

    @Override
    public void init() throws ServletException {
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String action = request.getParameter("action");

        try {

            if ("detail".equals(action)) {
                showInvoiceDetail(request, response, userId);
            } else {
                List<Invoice> invoices = invoiceDAO.getInvoicesByCustomerId(userId);
                request.setAttribute("invoices", invoices);
                request.getRequestDispatcher("/customer/SearchInvoice.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/customer/SearchInvoice.jsp").forward(request, response);
        }
    }

    private void showInvoiceDetail(HttpServletRequest request, HttpServletResponse response, int customerId)
            throws ServletException, IOException, SQLException {

        String invoiceIdParam = request.getParameter("invoiceId");

        if (invoiceIdParam == null || invoiceIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Mã hoá đơn không hợp lệ.");
            request.getRequestDispatcher("/customer/InvoiceDetail.jsp").forward(request, response);
            return;
        }

        try {
            int invoiceId = Integer.parseInt(invoiceIdParam);
            Invoice invoice = invoiceDAO.getInvoiceDetail(invoiceId);

            if (invoice == null || invoice.getCustomer().getId() != customerId) {
                request.setAttribute("error", "Không tìm thấy hoá đơn hoặc bạn không có quyền truy cập.");
            } else {
                request.setAttribute("invoice", invoice);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Mã hoá đơn không hợp lệ.");
        }

        request.getRequestDispatcher("/customer/InvoiceDetail.jsp").forward(request, response);
    }
}


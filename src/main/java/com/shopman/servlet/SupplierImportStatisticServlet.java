package com.shopman.servlet;

import com.shopman.dao.SupplierImportStatisticDAO;
import com.shopman.model.SupplierImportStatistic;
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

@WebServlet("/supplier-import-statistic")
public class SupplierImportStatisticServlet extends HttpServlet {
    private SupplierImportStatisticDAO supplierImportStatisticDAO;

    @Override
    public void init() {
        supplierImportStatisticDAO = new SupplierImportStatisticDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        try {
            if (startDateStr != null && endDateStr != null) {
                Date startDate = Date.valueOf(startDateStr);
                Date endDate = Date.valueOf(endDateStr);

                List<SupplierImportStatistic> statistics = supplierImportStatisticDAO.getListSupplierImportStatistic(startDate, endDate);
                request.setAttribute("listSupplierImportStatistic", statistics);
                request.setAttribute("startDate", startDateStr);
                request.setAttribute("endDate", endDateStr);
            }

            request.getRequestDispatcher("/manager/SupplierStatistic.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/manager/SupplierStatistic.jsp").forward(request, response);
        }
    }
}


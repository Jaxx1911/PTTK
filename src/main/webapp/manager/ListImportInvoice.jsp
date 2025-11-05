<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết thống kê - ${supplier.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; }
        .detail-container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .detail-card { background: white; border-radius: 15px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); padding: 40px; }
        .page-title { font-size: 1.8rem; font-weight: 700; color: #2d3748; text-align: center; margin-bottom: 10px; }
        .supplier-info { font-size: 1rem; color: #4a5568; text-align: center; margin-bottom: 30px; }
        .date-range { background: #f7fafc; padding: 15px; border-radius: 10px; text-align: center; margin-bottom: 30px; font-weight: 600; }
        .import-table th { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; font-weight: 600; text-align: center; padding: 15px 10px; border: none; }
        .import-table td { text-align: center; padding: 12px 10px; vertical-align: middle; border-bottom: 1px solid #e2e8f0; }
        .import-table tbody tr:hover { background: #f7fafc; cursor: pointer; }
        .invoice-link { color: #667eea; text-decoration: none; font-weight: 600; }
        .invoice-link:hover { color: #764ba2; text-decoration: underline; }
    </style>
</head>
<body>
    <div class="detail-container">
        <div class="detail-card">
            <h1 class="page-title">Chi tiết thống kê</h1>
            <div class="supplier-info">Nhà cung cấp: <strong>${supplier.name}</strong></div>
            <div class="date-range">${startDate} - ${endDate}</div>

            <c:if test="${not empty listImportInvoice}">
                <table class="table table-bordered import-table">
                    <thead>
                        <tr>
                            <th style="width: 10%;">STT</th>
                            <th style="width: 25%;">Mã hóa đơn nhập</th>
                            <th style="width: 25%;">Số lượng hàng nhập</th>
                            <th style="width: 25%;">Tổng giá trị</th>
                            <th style="width: 15%;">Thời gian nhập</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="invoice" items="${listImportInvoice}" varStatus="status">
                            <tr onclick="window.location.href='${pageContext.request.contextPath}/import-invoice?action=detail&invoiceId=${invoice.id}'">
                                <td>${status.index + 1}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/import-invoice?action=detail&invoiceId=${invoice.id}"
                                       class="invoice-link">${invoice.id}</a>
                                </td>
                                <td>
                                    <c:set var="totalQty" value="0" />
                                    <c:forEach var="product" items="${invoice.importProductDetail}">
                                        <c:set var="totalQty" value="${totalQty + product.quantity}" />
                                    </c:forEach>
                                    <fmt:formatNumber value="${totalQty}" type="number" maxFractionDigits="0" groupingUsed="true" />
                                </td>
                                <td><fmt:formatNumber value="${invoice.totalAmount}" type="number" groupingUsed="true" /> VNĐ</td>
                                <td><fmt:formatDate value="${invoice.importDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty listImportInvoice}">
                <div class="alert alert-info text-center">Không có lần nhập hàng nào trong khoảng thời gian này.</div>
            </c:if>

            <div class="text-center">
                <a href="${pageContext.request.contextPath}/supplier-import-statistic?startDate=${startDate}&endDate=${endDate}"
                   class="btn btn-secondary">← Quay lại thống kê</a>
            </div>
        </div>
    </div>
</body>
</html>


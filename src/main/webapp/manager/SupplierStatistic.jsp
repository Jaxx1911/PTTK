<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê nhà cung cấp theo lượng hàng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body { background: #f8f9fa; }
        .report-container { max-width: 1400px; margin: 30px auto; padding: 0 20px; }
        .report-card { background: white; border-radius: 15px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); padding: 40px; }
        .page-title { font-size: 1.8rem; font-weight: 700; color: #2d3748; text-align: center; margin-bottom: 30px; border-bottom: 3px solid #667eea; padding-bottom: 15px; }
        .date-filter { background: #f7fafc; padding: 20px; border-radius: 10px; margin-bottom: 30px; }
        .supplier-table th { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; font-weight: 600; text-align: center; padding: 15px 10px; border: none; }
        .supplier-table td { text-align: center; padding: 12px 10px; vertical-align: middle; border-bottom: 1px solid #e2e8f0; }
        .supplier-table tbody tr:hover { background: #f7fafc; cursor: pointer; }
        .supplier-link { color: #667eea; text-decoration: none; font-weight: 600; }
        .supplier-link:hover { color: #764ba2; text-decoration: underline; }
    </style>
</head>
<body>
    <div class="report-container">
        <div class="report-card">
            <h1 class="page-title">Thống kê nhà cung cấp theo lượng hàng nhập</h1>

            <div class="date-filter">
                <form method="get" action="${pageContext.request.contextPath}/supplier-import-statistic" class="row g-3">
                    <div class="col-md-5">
                        <label for="startDate" class="form-label">Ngày bắt đầu:</label>
                        <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}" required>
                    </div>
                    <div class="col-md-5">
                        <label for="endDate" class="form-label">Ngày kết thúc:</label>
                        <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}" required>
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">Xem thống kê</button>
                    </div>
                </form>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <c:if test="${not empty listSupplierImportStatistic}">
                <table class="table table-bordered supplier-table">
                    <thead>
                        <tr>
                            <th style="width: 10%;">STT</th>
                            <th style="width: 30%;">Tên nhà cung cấp</th>
                            <th style="width: 20%;">Số lượng hàng nhập</th>
                            <th style="width: 20%;">Tổng giá trị hàng nhập</th>
                            <th style="width: 20%;">Tổng số lần nhập</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="statistic" items="${listSupplierImportStatistic}" varStatus="status">
                            <tr onclick="window.location.href='${pageContext.request.contextPath}/import-invoice?supplierId=${statistic.supplier.id}&supplierName=${statistic.supplier.name}&startDate=${startDate}&endDate=${endDate}'">
                                <td>${status.index + 1}</td>
                                <td class="text-start">
                                    <a href="${pageContext.request.contextPath}/import-invoice?supplierId=${statistic.supplier.id}&supplierName=${statistic.supplier.name}&startDate=${startDate}&endDate=${endDate}"
                                       class="supplier-link">${statistic.supplier.name}</a>
                                </td>
                                <td><fmt:formatNumber value="${statistic.importAmount}" type="number" maxFractionDigits="0" groupingUsed="true" /></td>
                                <td><fmt:formatNumber value="${statistic.totalImportPrice}" type="number" groupingUsed="true" /> VNĐ</td>
                                <td>${statistic.totalImports}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty listSupplierImportStatistic && not empty startDate}">
                <div class="alert alert-info text-center">Không có dữ liệu trong khoảng thời gian này.</div>
            </c:if>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/manager/HomeManager.jsp" class="btn btn-secondary">← Quay lại</a>
            </div>
        </div>
    </div>
</body>
</html>


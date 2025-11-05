<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách hóa đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
        }
        .invoice-container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 0 20px;
        }
        .invoice-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 40px;
        }
        .page-title {
            font-size: 2rem;
            font-weight: 700;
            color: #2d3748;
            text-align: center;
            margin-bottom: 10px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 15px;
        }
        .customer-info {
            font-size: 1.1rem;
            color: #4a5568;
            text-align: center;
            margin-bottom: 30px;
            font-weight: 500;
        }
        .section-subtitle {
            font-size: 1rem;
            color: #718096;
            margin-bottom: 20px;
        }
        .invoice-table {
            margin-top: 20px;
        }
        .invoice-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            font-weight: 600;
            text-align: center;
            padding: 15px 10px;
            border: none;
        }
        .invoice-table td {
            text-align: center;
            padding: 12px 10px;
            vertical-align: middle;
            border-bottom: 1px solid #e2e8f0;
        }
        .invoice-table tbody tr:hover {
            background: #f7fafc;
            cursor: pointer;
        }
        .invoice-link {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.2s;
        }
        .invoice-link:hover {
            color: #764ba2;
            text-decoration: underline;
        }
        .btn-back {
            background: #6c757d;
            color: white;
            padding: 10px 30px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
            display: inline-block;
            margin-top: 20px;
        }
        .btn-back:hover {
            background: #5a6268;
            color: white;
            transform: translateY(-2px);
        }
        .alert {
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="invoice-container">
        <div class="invoice-card">
            <h1 class="page-title">Danh sách hóa đơn</h1>

            <c:if test="${not empty sessionScope.userName}">
                <div class="customer-info">
                    Khách hàng: <strong>${sessionScope.userName}</strong>
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <c:choose>
                <c:when test="${not empty invoices}">
                    <div class="section-subtitle">Danh sách hóa đơn đã đặt:</div>

                    <table class="table table-bordered invoice-table">
                        <thead>
                            <tr>
                                <th style="width: 10%;">STT</th>
                                <th style="width: 25%;">Mã hóa đơn</th>
                                <th style="width: 25%;">Ngày đặt</th>
                                <th style="width: 20%;">Tổng tiền</th>
                                <th style="width: 20%;">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="invoice" items="${invoices}" varStatus="status">
                                <tr onclick="window.location.href='${pageContext.request.contextPath}/invoice?action=detail&invoiceId=${invoice.id}'">
                                    <td>${status.index + 1}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/invoice?action=detail&invoiceId=${invoice.id}"
                                           class="invoice-link">
                                            ${invoice.id}
                                        </a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${invoice.date}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${invoice.totalAmount}" type="number" groupingUsed="true" /> VNĐ
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${invoice.status == 'pending'}">
                                                <span class="badge bg-warning">Chờ duyệt</span>
                                            </c:when>
                                            <c:when test="${invoice.status == 'approved'}">
                                                <span class="badge bg-success">Đã duyệt</span>
                                            </c:when>
                                            <c:when test="${invoice.status == 'delivered'}">
                                                <span class="badge bg-info">Đã giao</span>
                                            </c:when>
                                            <c:when test="${invoice.status == 'cancelled'}">
                                                <span class="badge bg-danger">Đã hủy</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${invoice.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center">
                        Bạn chưa có hóa đơn nào.
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="text-center">
                <a href="${pageContext.request.contextPath}/customer/HomeCustomer.jsp" class="btn-back">
                    ← Quay lại
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


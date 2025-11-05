<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết hóa đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
        }
        .invoice-detail-container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 0 20px;
        }
        .detail-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 50px;
        }
        .page-title {
            font-size: 2rem;
            font-weight: 700;
            color: #2d3748;
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 15px;
        }
        .info-section {
            margin-bottom: 30px;
        }
        .info-row {
            font-size: 1.1rem;
            margin-bottom: 10px;
            color: #4a5568;
        }
        .info-label {
            font-weight: 600;
            color: #2d3748;
        }
        .section-subtitle {
            font-size: 1.2rem;
            font-weight: 600;
            color: #2d3748;
            margin: 25px 0 15px 0;
        }
        .product-table {
            margin-top: 20px;
        }
        .product-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            font-weight: 600;
            text-align: center;
            padding: 15px 10px;
            border: none;
            font-size: 0.95rem;
        }
        .product-table td {
            text-align: center;
            padding: 12px 10px;
            vertical-align: middle;
            border-bottom: 1px solid #e2e8f0;
        }
        .product-table .text-left {
            text-align: left !important;
        }
        .product-table .text-right {
            text-align: right !important;
        }
        .product-table tfoot td {
            font-weight: 600;
            background: #f7fafc;
            border-top: 2px solid #667eea;
        }
        .summary-section {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #e2e8f0;
        }
        .summary-row {
            display: flex;
            justify-content: space-between;
            font-size: 1.1rem;
            margin-bottom: 10px;
            padding: 5px 0;
        }
        .summary-row.total {
            font-size: 1.3rem;
            font-weight: 700;
            color: #2d3748;
            padding-top: 15px;
            border-top: 2px solid #667eea;
        }
        .status-badge {
            display: inline-block;
            padding: 8px 20px;
            border-radius: 20px;
            font-weight: 600;
            font-size: 1rem;
        }
        .status-pending {
            background: #fef3c7;
            color: #92400e;
        }
        .status-approved {
            background: #d1fae5;
            color: #065f46;
        }
        .status-delivered {
            background: #dbeafe;
            color: #1e40af;
        }
        .btn-back {
            background: #6c757d;
            color: white;
            padding: 12px 40px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
            display: inline-block;
            margin-top: 30px;
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
    <div class="invoice-detail-container">
        <div class="detail-card">
            <h1 class="page-title">Chi tiết hóa đơn</h1>

            <c:choose>
                <c:when test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/invoice" class="btn-back">
                            ← Quay lại danh sách
                        </a>
                    </div>
                </c:when>

                <c:when test="${not empty invoice}">
                    <div class="info-section">
                        <div class="info-row">
                            <span class="info-label">Khách hàng:</span>
                            ${invoice.customer.name}
                        </div>
                        <div class="info-row">
                            <span class="info-label">Mã hóa đơn:</span>
                            ${invoice.id}
                        </div>
                        <div class="info-row">
                            <span class="info-label">Ngày đặt:</span>
                            <fmt:formatDate value="${invoice.date}" pattern="dd/MM/yyyy" />
                        </div>
                        <c:if test="${not empty invoice.saleStaff}">
                            <div class="info-row">
                                <span class="info-label">Nhân viên bán hàng:</span>
                                ${invoice.saleStaff.name}
                            </div>
                        </c:if>
                        <c:if test="${not empty invoice.deliveryStaff}">
                            <div class="info-row">
                                <span class="info-label">Nhân viên giao hàng:</span>
                                ${invoice.deliveryStaff.name}
                            </div>
                        </c:if>
                    </div>

                    <div class="section-subtitle">Danh sách mặt hàng:</div>

                    <table class="table table-bordered product-table">
                        <thead>
                            <tr>
                                <th style="width: 8%;">STT</th>
                                <th style="width: 30%;">Tên mặt hàng</th>
                                <th style="width: 15%;">Đơn vị</th>
                                <th style="width: 15%;">Đơn giá</th>
                                <th style="width: 12%;">Số lượng</th>
                                <th style="width: 20%;">Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${invoice.productsDetail}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td class="text-left">${item.product.name}</td>
                                    <td>${item.product.unit}</td>
                                    <td class="text-right">
                                        <fmt:formatNumber value="${item.unitPrice}" type="number" groupingUsed="true" />
                                    </td>
                                    <td>${item.quantity}</td>
                                    <td class="text-right">
                                        <fmt:formatNumber value="${item.subtotal}" type="number" groupingUsed="true" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="5" class="text-right">
                                    <strong>Tổng cộng</strong>
                                </td>
                                <td class="text-right">
                                    <strong>
                                        <fmt:formatNumber value="${invoice.totalAmount}" type="number" groupingUsed="true" />
                                    </strong>
                                </td>
                            </tr>
                        </tfoot>
                    </table>

                    <div class="summary-section">
                        <div class="summary-row total">
                            <span>Tổng tiền thanh toán:</span>
                            <span>
                                <fmt:formatNumber value="${invoice.totalAmount}" type="number" groupingUsed="true" /> VNĐ
                            </span>
                        </div>
                        <div class="summary-row">
                            <span>Trạng thái:</span>
                            <span>
                                <c:choose>
                                    <c:when test="${invoice.status == 'pending'}">
                                        <span class="status-badge status-pending">Chờ duyệt</span>
                                    </c:when>
                                    <c:when test="${invoice.status == 'approved'}">
                                        <span class="status-badge status-approved">Đã duyệt</span>
                                    </c:when>
                                    <c:when test="${invoice.status == 'delivered'}">
                                        <span class="status-badge status-delivered">Đã giao</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge">${invoice.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>

                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/invoice" class="btn-back">
                            ← Quay lại danh sách
                        </a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="alert alert-warning text-center" role="alert">
                        Không tìm thấy thông tin hóa đơn.
                    </div>
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/invoice" class="btn-back">
                            ← Quay lại danh sách
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


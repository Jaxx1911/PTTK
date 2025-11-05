<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết hóa đơn nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
        }
        .invoice-container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .invoice-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 40px;
        }
        .page-title {
            font-size: 1.8rem;
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
            font-size: 1rem;
            margin-bottom: 10px;
            color: #4a5568;
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
        }
        .info-label {
            font-weight: 600;
            color: #2d3748;
        }
        .product-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            font-weight: 600;
            text-align: center;
            padding: 15px 10px;
            border: none;
        }
        .product-table td {
            text-align: center;
            padding: 12px 10px;
            vertical-align: middle;
            border-bottom: 1px solid #e2e8f0;
        }
        .product-table .text-start {
            text-align: left !important;
        }
        .product-table .text-end {
            text-align: right !important;
        }
        .product-table tfoot td {
            font-weight: 700;
            background: #f7fafc;
            border-top: 2px solid #667eea;
            font-size: 1.1rem;
        }
        .btn-back {
            background: #6c757d;
            color: white;
            padding: 12px 40px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            display: inline-block;
            margin-top: 20px;
        }
        .btn-back:hover {
            background: #5a6268;
            color: white;
        }
    </style>
</head>
<body>
    <div class="invoice-container">
        <div class="invoice-card">
            <h1 class="page-title">Chi tiết hóa đơn nhập: ${importInvoice.id}</h1>

            <div class="info-section">
                <div class="info-row">
                    <span class="info-label">Người nhập:</span>
                    <span>${importInvoice.manager.name}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Nhà cung cấp:</span>
                    <span>${importInvoice.supplier.name}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Thời gian nhập:</span>
                    <span><fmt:formatDate value="${importInvoice.importDate}" pattern="dd/MM/yyyy HH:mm" /></span>
                </div>
            </div>

            <h5 class="mb-3">Danh sách mặt hàng:</h5>

            <table class="table table-bordered product-table">
                <thead>
                    <tr>
                        <th style="width: 8%;">STT</th>
                        <th style="width: 25%;">Tên mặt hàng</th>
                        <th style="width: 15%;">Mã mặt hàng</th>
                        <th style="width: 12%;">Đơn vị</th>
                        <th style="width: 12%;">Số lượng</th>
                        <th style="width: 14%;">Đơn giá</th>
                        <th style="width: 14%;">Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${importInvoice.importProductDetail}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td class="text-start">${item.product.name}</td>
                            <td>${item.product.id}</td>
                            <td>${item.product.unit}</td>
                            <td>
                                <fmt:formatNumber value="${item.quantity}" type="number"
                                                 maxFractionDigits="0" groupingUsed="true" />
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber value="${item.unitImportPrice}" type="number"
                                                 groupingUsed="true" />
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber value="${item.subTotal}" type="number"
                                                 groupingUsed="true" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6" class="text-end">Tổng cộng:</td>
                        <td class="text-end">
                            <fmt:formatNumber value="${importInvoice.totalAmount}" type="number"
                                             groupingUsed="true" />
                        </td>
                    </tr>
                </tfoot>
            </table>

            <div class="text-center">
                <a href="javascript:history.back()" class="btn-back">
                    ← Quay lại
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


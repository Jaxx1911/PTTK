<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Kiểm tra session
    if (session.getAttribute("userId") == null) {
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ quản lý</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body {
            background: #667eea;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .manager-home {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 50px;
            max-width: 600px;
            width: 100%;
        }
        .page-title {
            font-size: 2.5rem;
            font-weight: 700;
            color: #2d3748;
            text-align: center;
            margin-bottom: 40px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 20px;
        }
        .menu-section {
            margin-bottom: 30px;
        }
        .menu-title {
            font-size: 1.3rem;
            font-weight: 600;
            color: #4a5568;
            margin-bottom: 15px;
            padding-left: 10px;
            border-left: 4px solid #667eea;
        }
        .menu-btn {
            width: 100%;
            padding: 15px;
            font-size: 1.1rem;
            font-weight: 600;
            border-radius: 10px;
            border: 2px solid #e2e8f0;
            background: white;
            color: #2d3748;
            text-decoration: none;
            display: block;
            text-align: center;
            margin-bottom: 10px;
            transition: all 0.3s ease;
        }
        .feature-card:hover {
            background: #667eea;
            color: white;
            border-color: #667eea;
            transform: translateX(5px);
        }
        .user-info {
            text-align: center;
            margin-bottom: 30px;
            color: #4a5568;
            font-size: 1.1rem;
        }
        .btn-logout {
            width: 100%;
            padding: 12px;
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            margin-top: 20px;
        }
        .btn-logout:hover {
            background: #5a6268;
            color: white;
        }
    </style>
</head>
<body>
    <div class="manager-home">
        <h1 class="page-title">Trang chủ quản lý</h1>

        <div class="user-info">
            <strong>Xin chào, <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : session.getAttribute("username") %>!</strong>
        </div>

        <div class="menu-section">
            <div class="menu-title">Xem báo cáo</div>
            <a href="${pageContext.request.contextPath}/supplier-import-statistic" class="menu-btn">
                ✓ Xem thống kê nhà cung cấp theo lượng hàng nhập
            </a>
            <a class="menu-btn">
                ✓ Xem thống kê khách hàng đặt nhiều
            </a>
        </div>

        <a href="${pageContext.request.contextPath}/logout" class="btn btn-logout">Đăng xuất</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


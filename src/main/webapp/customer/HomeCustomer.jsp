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
    <title>Trang chủ khách hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        .customer-home {
            min-height: 100vh;
            background: #667eea;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .home-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 60px 80px;
            text-align: center;
            min-width: 400px;
        }
        .home-card h1 {
            font-size: 2.5rem;
            font-weight: 700;
            color: #2d3748;
            margin-bottom: 50px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 20px;
        }
        .search-btn {
            width: 100%;
            padding: 20px;
            font-size: 1.3rem;
            font-weight: 600;
            border-radius: 15px;
            background: #667eea;
            border: none;
            color: white;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
            text-decoration: none;
            display: block;
        }
        .search-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 25px rgba(102, 126, 234, 0.6);
            background: #5568d3;
            color: white;
        }
        .user-info {
            text-align: center;
            margin-bottom: 30px;
            color: #4a5568;
            font-size: 1.1rem;
        }
        .btn-logout {
            margin-top: 20px;
            padding: 10px 30px;
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 10px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-logout:hover {
            background: #5a6268;
            color: white;

        }
    </style>
</head>

<body>
    <div class="customer-home">
        <div class="home-card">
            <h1>Trang chủ khách hàng</h1>
            <a href="${pageContext.request.contextPath}/invoice" class="btn search-btn">
                Tra cứu hóa đơn
            </a>
        </div>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Đăng xuất</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


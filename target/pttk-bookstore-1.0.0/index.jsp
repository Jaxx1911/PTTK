<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PTTK Bookstore - Welcome</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-book"></i> PTTK Bookstore
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="books?action=search">Browse Books</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                    <i class="fas fa-user"></i> ${sessionScope.user.firstName}
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="dashboard">Dashboard</a></li>
                                    <li><a class="dropdown-item" href="books">Manage Books</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="logout">Logout</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="register">Register</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="hero-section bg-light py-5">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="display-4 fw-bold text-primary">Welcome to PTTK Bookstore</h1>
                    <p class="lead">Discover your next favorite book from our extensive collection. We offer both English and Vietnamese literature, programming books, and much more.</p>
                    <p class="text-muted">Experience seamless book browsing, secure ordering, and excellent customer service.</p>
                    <div class="mt-4">
                        <c:choose>
                            <c:when test="${sessionScope.user != null}">
                                <a href="books?action=search" class="btn btn-primary btn-lg me-3">
                                    <i class="fas fa-search"></i> Browse Books
                                </a>
                                <a href="dashboard" class="btn btn-outline-primary btn-lg">
                                    <i class="fas fa-tachometer-alt"></i> Dashboard
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="register" class="btn btn-primary btn-lg me-3">
                                    <i class="fas fa-user-plus"></i> Get Started
                                </a>
                                <a href="books?action=search" class="btn btn-outline-primary btn-lg">
                                    <i class="fas fa-book-open"></i> Browse Books
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="col-lg-6">
                    <img src="https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500&h=400&fit=crop" 
                         alt="Bookstore" class="img-fluid rounded shadow">
                </div>
            </div>
        </div>
    </div>

    <div class="container my-5">
        <div class="row">
            <div class="col-lg-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center">
                        <i class="fas fa-book fa-3x text-primary mb-3"></i>
                        <h5 class="card-title">Extensive Collection</h5>
                        <p class="card-text">Browse through thousands of books across various categories including programming, literature, and more.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center">
                        <i class="fas fa-user-shield fa-3x text-primary mb-3"></i>
                        <h5 class="card-title">Secure Platform</h5>
                        <p class="card-text">Your account and personal information are protected with industry-standard security measures.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center">
                        <i class="fas fa-shipping-fast fa-3x text-primary mb-3"></i>
                        <h5 class="card-title">Easy Ordering</h5>
                        <p class="card-text">Simple and intuitive ordering process with real-time inventory management.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5>PTTK Bookstore</h5>
                    <p class="text-muted">Your trusted partner for all your reading needs.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="text-muted">
                        &copy; 2024 PTTK Bookstore. All rights reserved.<br>
                        Built with JSP, Servlet, JDBC & MySQL
                    </p>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 
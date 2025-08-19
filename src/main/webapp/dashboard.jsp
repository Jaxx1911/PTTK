<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - PTTK Bookstore</title>
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
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="dashboard">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="books">Books</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user"></i> ${sessionScope.user.firstName}
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="books">Manage Books</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h2 class="mb-4">
                    <i class="fas fa-tachometer-alt"></i> 
                    Welcome back, ${sessionScope.user.firstName}!
                </h2>
                
                <div class="row">
                    <div class="col-lg-4 mb-4">
                        <div class="card dashboard-card">
                            <div class="card-body text-center">
                                <i class="fas fa-book fa-3x mb-3"></i>
                                <h5 class="card-title">Manage Books</h5>
                                <p class="card-text">Add, edit, or delete books in your inventory.</p>
                                <a href="books" class="btn btn-light">
                                    <i class="fas fa-arrow-right"></i> Go to Books
                                </a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-4 mb-4">
                        <div class="card dashboard-card">
                            <div class="card-body text-center">
                                <i class="fas fa-search fa-3x mb-3"></i>
                                <h5 class="card-title">Browse Collection</h5>
                                <p class="card-text">Search and explore the available books.</p>
                                <a href="books?action=search" class="btn btn-light">
                                    <i class="fas fa-arrow-right"></i> Browse Books
                                </a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-4 mb-4">
                        <div class="card dashboard-card">
                            <div class="card-body text-center">
                                <i class="fas fa-plus fa-3x mb-3"></i>
                                <h5 class="card-title">Add New Book</h5>
                                <p class="card-text">Add a new book to the collection.</p>
                                <a href="books?action=new" class="btn btn-light">
                                    <i class="fas fa-arrow-right"></i> Add Book
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Quick Stats -->
                <div class="row mt-4">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="mb-0"><i class="fas fa-chart-bar"></i> Quick Actions</h5>
                            </div>
                            <div class="card-body">
                                <div class="row text-center">
                                    <div class="col-md-3 mb-3">
                                        <a href="books" class="btn btn-outline-primary btn-lg w-100">
                                            <i class="fas fa-list"></i><br>
                                            <small>View All Books</small>
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="books?action=search&category=Programming" class="btn btn-outline-success btn-lg w-100">
                                            <i class="fas fa-code"></i><br>
                                            <small>Programming Books</small>
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="books?action=search&category=Literature" class="btn btn-outline-info btn-lg w-100">
                                            <i class="fas fa-book-open"></i><br>
                                            <small>Literature</small>
                                        </a>
                                    </div>
                                    <div class="col-md-3 mb-3">
                                        <a href="books?action=new" class="btn btn-outline-warning btn-lg w-100">
                                            <i class="fas fa-plus-circle"></i><br>
                                            <small>Add New Book</small>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- User Info -->
                <div class="row mt-4">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="mb-0"><i class="fas fa-user-circle"></i> Account Information</h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Full Name:</strong> ${sessionScope.user.fullName}</p>
                                        <p><strong>Username:</strong> ${sessionScope.user.username}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Email:</strong> ${sessionScope.user.email}</p>
                                        <p><strong>Member Since:</strong> ${sessionScope.user.createdAt}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container text-center">
            <p class="mb-0 text-muted">
                &copy; 2024 PTTK Bookstore. All rights reserved.
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 
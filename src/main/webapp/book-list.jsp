<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Management - PTTK Bookstore</title>
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
                        <a class="nav-link active" href="books">Books</a>
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
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="logout">Logout</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="login">Login</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-books"></i> Book Management</h2>
                    <c:if test="${sessionScope.user != null}">
                        <a href="books?action=new" class="btn btn-primary">
                            <i class="fas fa-plus"></i> Add New Book
                        </a>
                    </c:if>
                </div>

                <!-- Success/Error Messages -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle"></i> ${successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Search and Filter Section -->
                <div class="card mb-4">
                    <div class="card-body">
                        <form action="books" method="get" class="row g-3">
                            <input type="hidden" name="action" value="search">
                            
                            <div class="col-md-6">
                                <label for="searchTerm" class="form-label">Search Books</label>
                                <input type="text" class="form-control" id="searchTerm" name="searchTerm" 
                                       value="${searchTerm}" placeholder="Search by title, author, or category...">
                            </div>
                            
                            <div class="col-md-4">
                                <label for="category" class="form-label">Filter by Category</label>
                                <select class="form-select" id="category" name="category">
                                    <option value="">All Categories</option>
                                    <c:forEach items="${categories}" var="cat">
                                        <option value="${cat}" ${selectedCategory eq cat ? 'selected' : ''}>${cat}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div class="col-md-2">
                                <label class="form-label">&nbsp;</label>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-outline-primary">
                                        <i class="fas fa-search"></i> Search
                                    </button>
                                </div>
                            </div>
                        </form>
                        
                        <c:if test="${not empty searchTerm or not empty selectedCategory}">
                            <div class="mt-2">
                                <a href="books" class="btn btn-sm btn-outline-secondary">
                                    <i class="fas fa-times"></i> Clear Filters
                                </a>
                            </div>
                        </c:if>
                    </div>
                </div>

                <!-- Books Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list"></i> Books 
                            <span class="badge bg-primary">${books.size()}</span>
                        </h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${empty books}">
                                <div class="text-center py-5">
                                    <i class="fas fa-book fa-3x text-muted mb-3"></i>
                                    <h5 class="text-muted">No books found</h5>
                                    <p class="text-muted">
                                        <c:choose>
                                            <c:when test="${not empty searchTerm or not empty selectedCategory}">
                                                Try adjusting your search criteria or 
                                                <a href="books" class="text-decoration-none">view all books</a>.
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${sessionScope.user != null}">
                                                    <a href="books?action=new" class="btn btn-primary">
                                                        <i class="fas fa-plus"></i> Add the first book
                                                    </a>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead class="table-dark">
                                            <tr>
                                                <th>ID</th>
                                                <th>Title</th>
                                                <th>Author</th>
                                                <th>Category</th>
                                                <th>Price</th>
                                                <th>Stock</th>
                                                <th>ISBN</th>
                                                <c:if test="${sessionScope.user != null}">
                                                    <th width="120">Actions</th>
                                                </c:if>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${books}" var="book">
                                                <tr>
                                                    <td>${book.id}</td>
                                                    <td>
                                                        <strong>${book.title}</strong>
                                                        <c:if test="${not empty book.description}">
                                                            <br><small class="text-muted">${book.description.length() > 50 ? book.description.substring(0, 50).concat('...') : book.description}</small>
                                                        </c:if>
                                                    </td>
                                                    <td>${book.author}</td>
                                                    <td>
                                                        <c:if test="${not empty book.category}">
                                                            <span class="badge bg-secondary">${book.category}</span>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <span class="fw-bold text-success">
                                                            $<fmt:formatNumber value="${book.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <span class="badge ${book.stockQuantity > 0 ? 'bg-success' : 'bg-danger'}">
                                                            ${book.stockQuantity}
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <small class="text-muted">${book.isbn}</small>
                                                    </td>
                                                    <c:if test="${sessionScope.user != null}">
                                                        <td>
                                                            <div class="btn-group" role="group">
                                                                <a href="books?action=edit&id=${book.id}" 
                                                                   class="btn btn-sm btn-outline-primary" title="Edit">
                                                                    <i class="fas fa-edit"></i>
                                                                </a>
                                                                <a href="books?action=delete&id=${book.id}" 
                                                                   class="btn btn-sm btn-outline-danger" title="Delete"
                                                                   onclick="return confirm('Are you sure you want to delete this book?')">
                                                                    <i class="fas fa-trash"></i>
                                                                </a>
                                                            </div>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
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
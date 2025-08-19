<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${book != null ? 'Edit' : 'Add'} Book - PTTK Bookstore</title>
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
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="books">Back to Books</a>
                <c:if test="${sessionScope.user != null}">
                    <a class="nav-link" href="logout">Logout</a>
                </c:if>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h3 class="mb-0">
                            <i class="fas ${book != null ? 'fa-edit' : 'fa-plus'}"></i>
                            ${book != null ? 'Edit Book' : 'Add New Book'}
                        </h3>
                    </div>
                    <div class="card-body p-4">
                        <!-- Display error message -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="books" method="post" id="bookForm">
                            <input type="hidden" name="action" value="${book != null ? 'update' : 'insert'}">
                            <c:if test="${book != null}">
                                <input type="hidden" name="id" value="${book.id}">
                            </c:if>

                            <div class="row">
                                <div class="col-md-8 mb-3">
                                    <label for="title" class="form-label">
                                        <i class="fas fa-heading"></i> Book Title *
                                    </label>
                                    <input type="text" class="form-control" id="title" name="title" 
                                           value="${book != null ? book.title : param.title}" 
                                           required maxlength="255" placeholder="Enter book title">
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label for="price" class="form-label">
                                        <i class="fas fa-dollar-sign"></i> Price *
                                    </label>
                                    <input type="number" class="form-control" id="price" name="price" 
                                           value="${book != null ? book.price : param.price}" 
                                           required min="0" step="0.01" placeholder="0.00">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="author" class="form-label">
                                        <i class="fas fa-user-edit"></i> Author *
                                    </label>
                                    <input type="text" class="form-control" id="author" name="author" 
                                           value="${book != null ? book.author : param.author}" 
                                           required maxlength="255" placeholder="Enter author name">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="category" class="form-label">
                                        <i class="fas fa-tags"></i> Category
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="category" name="category" 
                                               value="${book != null ? book.category : param.category}" 
                                               maxlength="100" placeholder="Enter or select category"
                                               list="categoryList">
                                        <datalist id="categoryList">
                                            <c:forEach items="${categories}" var="cat">
                                                <option value="${cat}">
                                            </c:forEach>
                                        </datalist>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="isbn" class="form-label">
                                        <i class="fas fa-barcode"></i> ISBN
                                    </label>
                                    <input type="text" class="form-control" id="isbn" name="isbn" 
                                           value="${book != null ? book.isbn : param.isbn}" 
                                           maxlength="20" placeholder="Enter ISBN">
                                    <div class="form-text">
                                        Optional. Must be unique if provided.
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="stockQuantity" class="form-label">
                                        <i class="fas fa-boxes"></i> Stock Quantity *
                                    </label>
                                    <input type="number" class="form-control" id="stockQuantity" name="stockQuantity" 
                                           value="${book != null ? book.stockQuantity : param.stockQuantity}" 
                                           required min="0" placeholder="0">
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="description" class="form-label">
                                    <i class="fas fa-align-left"></i> Description
                                </label>
                                <textarea class="form-control" id="description" name="description" rows="4" 
                                          placeholder="Enter book description (optional)">${book != null ? book.description : param.description}</textarea>
                                <div class="form-text">
                                    Optional. Provide a brief description of the book.
                                </div>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="books" class="btn btn-secondary">
                                    <i class="fas fa-times"></i> Cancel
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas ${book != null ? 'fa-save' : 'fa-plus'}"></i>
                                    ${book != null ? 'Update Book' : 'Add Book'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Book Preview (if editing) -->
                <c:if test="${book != null}">
                    <div class="card mt-4">
                        <div class="card-header">
                            <h5 class="mb-0"><i class="fas fa-eye"></i> Current Book Information</h5>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>ID:</strong> ${book.id}</p>
                                    <p><strong>Created:</strong> ${book.createdAt}</p>
                                </div>
                                <div class="col-md-6">
                                    <p><strong>Last Updated:</strong> ${book.updatedAt}</p>
                                    <p><strong>Status:</strong> 
                                        <span class="badge ${book.stockQuantity > 0 ? 'bg-success' : 'bg-danger'}">
                                            ${book.stockQuantity > 0 ? 'In Stock' : 'Out of Stock'}
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
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
    <script>
        // Form validation
        document.getElementById('bookForm').addEventListener('submit', function(e) {
            const price = parseFloat(document.getElementById('price').value);
            const stockQuantity = parseInt(document.getElementById('stockQuantity').value);
            
            if (price <= 0) {
                e.preventDefault();
                alert('Price must be greater than 0.');
                document.getElementById('price').focus();
                return;
            }
            
            if (stockQuantity < 0) {
                e.preventDefault();
                alert('Stock quantity cannot be negative.');
                document.getElementById('stockQuantity').focus();
                return;
            }
        });

        // Auto-capitalize title and author
        document.getElementById('title').addEventListener('input', function() {
            this.value = this.value.replace(/\b\w/g, l => l.toUpperCase());
        });

        document.getElementById('author').addEventListener('input', function() {
            this.value = this.value.replace(/\b\w/g, l => l.toUpperCase());
        });
    </script>
</body>
</html> 
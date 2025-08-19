<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - PTTK Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-book"></i> PTTK Bookstore
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp">Home</a>
                <a class="nav-link" href="login">Login</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-sm mt-4 mb-5">
                    <div class="card-header bg-primary text-white text-center">
                        <h3><i class="fas fa-user-plus"></i> Create New Account</h3>
                    </div>
                    <div class="card-body p-4">
                        <!-- Display error message -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="register" method="post" id="registrationForm">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="firstName" class="form-label">
                                        <i class="fas fa-user"></i> First Name *
                                    </label>
                                    <input type="text" class="form-control" id="firstName" name="firstName" 
                                           value="${param.firstName}" required maxlength="50">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="lastName" class="form-label">
                                        <i class="fas fa-user"></i> Last Name *
                                    </label>
                                    <input type="text" class="form-control" id="lastName" name="lastName" 
                                           value="${param.lastName}" required maxlength="50">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="username" class="form-label">
                                    <i class="fas fa-at"></i> Username *
                                </label>
                                <input type="text" class="form-control" id="username" name="username" 
                                       value="${param.username}" required maxlength="50" pattern="[a-zA-Z0-9_]+"
                                       title="Username can only contain letters, numbers, and underscores">
                                <div class="form-text">
                                    3-50 characters. Letters, numbers, and underscores only.
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">
                                    <i class="fas fa-envelope"></i> Email Address *
                                </label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${param.email}" required maxlength="100">
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="password" class="form-label">
                                        <i class="fas fa-lock"></i> Password *
                                    </label>
                                    <input type="password" class="form-control" id="password" name="password" 
                                           required minlength="6" autocomplete="new-password">
                                    <div class="form-text">
                                        Minimum 6 characters.
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="confirmPassword" class="form-label">
                                        <i class="fas fa-lock"></i> Confirm Password *
                                    </label>
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                                           required minlength="6" autocomplete="new-password">
                                </div>
                            </div>

                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="agreeTerms" required>
                                <label class="form-check-label" for="agreeTerms">
                                    I agree to the <a href="#" class="text-decoration-none">Terms of Service</a> 
                                    and <a href="#" class="text-decoration-none">Privacy Policy</a> *
                                </label>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-user-plus"></i> Create Account
                                </button>
                            </div>
                        </form>

                        <hr>

                        <div class="text-center">
                            <p class="mb-0">
                                Already have an account? 
                                <a href="login" class="text-decoration-none">
                                    <i class="fas fa-sign-in-alt"></i> Login here
                                </a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="bg-dark text-white py-3">
        <div class="container text-center">
            <p class="mb-0 text-muted">
                &copy; 2024 PTTK Bookstore. All rights reserved.
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Client-side password confirmation validation
        document.getElementById('registrationForm').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match. Please check and try again.');
                document.getElementById('confirmPassword').focus();
            }
        });

        // Real-time password confirmation check
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('password').value;
            const confirmPassword = this.value;
            
            if (password !== confirmPassword && confirmPassword !== '') {
                this.setCustomValidity('Passwords do not match');
            } else {
                this.setCustomValidity('');
            }
        });
    </script>
</body>
</html> 
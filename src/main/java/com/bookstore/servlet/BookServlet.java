package com.bookstore.servlet;

import com.bookstore.dao.BookDAO;
import com.bookstore.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;
    
    @Override
    public void init() {
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            case "search":
                searchBooks(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "insert";
        }
        
        switch (action) {
            case "insert":
                insertBook(request, response);
                break;
            case "update":
                updateBook(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }
    
    private void listBooks(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Book> books = bookDAO.selectAllBooks();
        List<String> categories = bookDAO.getDistinctCategories();
        
        request.setAttribute("books", books);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("book-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<String> categories = bookDAO.getDistinctCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("book-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book existingBook = bookDAO.selectBook(id);
            
            if (existingBook != null) {
                List<String> categories = bookDAO.getDistinctCategories();
                request.setAttribute("book", existingBook);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("book-form.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Book not found.");
                listBooks(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid book ID.");
            listBooks(request, response);
        }
    }
    
    private void insertBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Validate and create book
        Book book = createBookFromRequest(request);
        String validationError = validateBook(book, 0); // 0 for new book
        
        if (validationError != null) {
            request.setAttribute("errorMessage", validationError);
            request.setAttribute("book", book);
            showNewForm(request, response);
            return;
        }
        
        try {
            boolean isInserted = bookDAO.insertBook(book);
            
            if (isInserted) {
                request.setAttribute("successMessage", "Book added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add book. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
        }
        
        listBooks(request, response);
    }
    
    private void updateBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = createBookFromRequest(request);
            book.setId(id);
            
            String validationError = validateBook(book, id);
            
            if (validationError != null) {
                request.setAttribute("errorMessage", validationError);
                request.setAttribute("book", book);
                showEditForm(request, response);
                return;
            }
            
            boolean isUpdated = bookDAO.updateBook(book);
            
            if (isUpdated) {
                request.setAttribute("successMessage", "Book updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update book. Please try again.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid book ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
        }
        
        listBooks(request, response);
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean isDeleted = bookDAO.deleteBook(id);
            
            if (isDeleted) {
                request.setAttribute("successMessage", "Book deleted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to delete book. Please try again.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid book ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
        }
        
        listBooks(request, response);
    }
    
    private void searchBooks(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchTerm = request.getParameter("searchTerm");
        String category = request.getParameter("category");
        
        List<Book> books;
        List<String> categories = bookDAO.getDistinctCategories();
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            books = bookDAO.searchBooks(searchTerm.trim());
            request.setAttribute("searchTerm", searchTerm);
        } else if (category != null && !category.trim().isEmpty()) {
            books = bookDAO.selectBooksByCategory(category);
            request.setAttribute("selectedCategory", category);
        } else {
            books = bookDAO.selectAllBooks();
        }
        
        request.setAttribute("books", books);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("book-list.jsp").forward(request, response);
    }
    
    private Book createBookFromRequest(HttpServletRequest request) {
        Book book = new Book();
        
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        
        String priceStr = request.getParameter("price");
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            try {
                book.setPrice(new BigDecimal(priceStr));
            } catch (NumberFormatException e) {
                book.setPrice(BigDecimal.ZERO);
            }
        }
        
        book.setDescription(request.getParameter("description"));
        book.setCategory(request.getParameter("category"));
        book.setIsbn(request.getParameter("isbn"));
        
        String stockStr = request.getParameter("stockQuantity");
        if (stockStr != null && !stockStr.trim().isEmpty()) {
            try {
                book.setStockQuantity(Integer.parseInt(stockStr));
            } catch (NumberFormatException e) {
                book.setStockQuantity(0);
            }
        }
        
        return book;
    }
    
    private String validateBook(Book book, int excludeBookId) {
        // Required field validation
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return "Title is required.";
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            return "Author is required.";
        }
        if (book.getPrice() == null || book.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return "Price must be greater than 0.";
        }
        if (book.getStockQuantity() < 0) {
            return "Stock quantity cannot be negative.";
        }
        
        // Length validation
        if (book.getTitle().length() > 255) {
            return "Title must not exceed 255 characters.";
        }
        if (book.getAuthor().length() > 255) {
            return "Author must not exceed 255 characters.";
        }
        if (book.getCategory() != null && book.getCategory().length() > 100) {
            return "Category must not exceed 100 characters.";
        }
        if (book.getIsbn() != null && book.getIsbn().length() > 20) {
            return "ISBN must not exceed 20 characters.";
        }
        
        // ISBN uniqueness check
        if (book.getIsbn() != null && !book.getIsbn().trim().isEmpty()) {
            if (bookDAO.isbnExists(book.getIsbn(), excludeBookId)) {
                return "ISBN already exists. Please use a different ISBN.";
            }
        }
        
        return null; // No validation errors
    }
} 
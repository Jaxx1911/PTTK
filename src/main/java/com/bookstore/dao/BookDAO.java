package com.bookstore.dao;

import com.bookstore.model.Book;
import com.bookstore.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    private static final String INSERT_BOOK_SQL = 
        "INSERT INTO books (title, author, price, description, category, isbn, stock_quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BOOK_BY_ID = 
        "SELECT id, title, author, price, description, category, isbn, stock_quantity, created_at, updated_at FROM books WHERE id = ?";
    
    private static final String SELECT_ALL_BOOKS = 
        "SELECT id, title, author, price, description, category, isbn, stock_quantity, created_at, updated_at FROM books ORDER BY title";
    
    private static final String DELETE_BOOK_SQL = "DELETE FROM books WHERE id = ?";
    
    private static final String UPDATE_BOOK_SQL = 
        "UPDATE books SET title = ?, author = ?, price = ?, description = ?, category = ?, isbn = ?, stock_quantity = ? WHERE id = ?";
    
    private static final String SEARCH_BOOKS_SQL = 
        "SELECT id, title, author, price, description, category, isbn, stock_quantity, created_at, updated_at FROM books " +
        "WHERE title LIKE ? OR author LIKE ? OR category LIKE ? ORDER BY title";
    
    private static final String SELECT_BOOKS_BY_CATEGORY = 
        "SELECT id, title, author, price, description, category, isbn, stock_quantity, created_at, updated_at FROM books WHERE category = ? ORDER BY title";
    
    private static final String CHECK_ISBN_EXISTS = 
        "SELECT COUNT(*) FROM books WHERE isbn = ? AND id != ?";
    
    // Create book
    public boolean insertBook(Book book) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setBigDecimal(3, book.getPrice());
            preparedStatement.setString(4, book.getDescription());
            preparedStatement.setString(5, book.getCategory());
            preparedStatement.setString(6, book.getIsbn());
            preparedStatement.setInt(7, book.getStockQuantity());
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }
    
    // Read book by ID
    public Book selectBook(int id) {
        Book book = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                book = mapResultSetToBook(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    
    // Read all books
    public List<Book> selectAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    // Update book
    public boolean updateBook(Book book) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_SQL)) {
            
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setBigDecimal(3, book.getPrice());
            statement.setString(4, book.getDescription());
            statement.setString(5, book.getCategory());
            statement.setString(6, book.getIsbn());
            statement.setInt(7, book.getStockQuantity());
            statement.setInt(8, book.getId());
            
            return statement.executeUpdate() > 0;
        }
    }
    
    // Delete book
    public boolean deleteBook(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_SQL)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }
    
    // Search books by title, author, or category
    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BOOKS_SQL)) {
            
            String searchPattern = "%" + searchTerm + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    // Get books by category
    public List<Book> selectBooksByCategory(String category) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_CATEGORY)) {
            
            preparedStatement.setString(1, category);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    // Check if ISBN exists (excluding current book for updates)
    public boolean isbnExists(String isbn, int excludeBookId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_ISBN_EXISTS)) {
            
            statement.setString(1, isbn);
            statement.setInt(2, excludeBookId);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Get distinct categories
    public List<String> getDistinctCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM books WHERE category IS NOT NULL ORDER BY category";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    
    // Update stock quantity
    public boolean updateStockQuantity(int bookId, int newQuantity) throws SQLException {
        String sql = "UPDATE books SET stock_quantity = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, newQuantity);
            statement.setInt(2, bookId);
            
            return statement.executeUpdate() > 0;
        }
    }
    
    // Utility method to map ResultSet to Book object
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setDescription(rs.getString("description"));
        book.setCategory(rs.getString("category"));
        book.setIsbn(rs.getString("isbn"));
        book.setStockQuantity(rs.getInt("stock_quantity"));
        book.setCreatedAt(rs.getTimestamp("created_at"));
        book.setUpdatedAt(rs.getTimestamp("updated_at"));
        return book;
    }
} 
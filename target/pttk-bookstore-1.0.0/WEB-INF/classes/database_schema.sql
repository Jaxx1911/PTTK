-- Database schema for PTTK Bookstore Application
-- Drop database if exists and create new one
DROP DATABASE IF EXISTS bookstore_db;
CREATE DATABASE bookstore_db;
USE bookstore_db;

-- Exercise 1: User Registration and Login Tables
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Exercise 2: Books Management Table
CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    isbn VARCHAR(20) UNIQUE,
    stock_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Exercise 3: Extended schema for complete bookstore

-- Address table
CREATE TABLE addresses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL DEFAULT 'Vietnam'
);

-- Customer table (extends users)
CREATE TABLE customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE,
    address_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

-- Account table (for customer accounts)
CREATE TABLE accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_type ENUM('SAVINGS', 'CHECKING', 'CREDIT') DEFAULT 'SAVINGS',
    balance DECIMAL(15, 2) DEFAULT 0.00,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Orders table
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    shipping_address_id INT,
    payment_method VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (shipping_address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

-- Order items table (OrderList - list of books in an order)
CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    book_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Shopping cart table (for temporary book selections)
CREATE TABLE cart_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    book_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    UNIQUE KEY unique_cart_item (customer_id, book_id)
);

-- Sample data insertion

-- Sample users
INSERT INTO users (username, email, password, first_name, last_name) VALUES
('admin', 'admin@bookstore.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye/TJmachRuuJhncnewjNVjKCBHZdNI2K', 'Admin', 'User'),
('john_doe', 'john@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye/TJmachRuuJhncnewjNVjKCBHZdNI2K', 'John', 'Doe'),
('jane_smith', 'jane@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye/TJmachRuuJhncnewjNVjKCBHZdNI2K', 'Jane', 'Smith');

-- Sample addresses
INSERT INTO addresses (street, city, state, postal_code, country) VALUES
('123 Main St', 'Ho Chi Minh City', 'Ho Chi Minh', '700000', 'Vietnam'),
('456 Oak Ave', 'Hanoi', 'Hanoi', '100000', 'Vietnam'),
('789 Pine Rd', 'Da Nang', 'Da Nang', '550000', 'Vietnam');

-- Sample customers
INSERT INTO customers (user_id, phone, date_of_birth, address_id) VALUES
(2, '+84123456789', '1990-05-15', 1),
(3, '+84987654321', '1985-10-20', 2);

-- Sample books
INSERT INTO books (title, author, price, description, category, isbn, stock_quantity) VALUES
('Effective Java', 'Joshua Bloch', 45.99, 'Best practices for Java programming', 'Programming', '978-0134685991', 50),
('Clean Code', 'Robert C. Martin', 42.50, 'A handbook of agile software craftsmanship', 'Programming', '978-0132350884', 30),
('Design Patterns', 'Gang of Four', 55.00, 'Elements of reusable object-oriented software', 'Programming', '978-0201633612', 25),
('Spring in Action', 'Craig Walls', 48.99, 'Covers Spring 5', 'Programming', '978-1617294945', 40),
('Java: The Complete Reference', 'Herbert Schildt', 59.99, 'Comprehensive Java guide', 'Programming', '978-1260440232', 35),
('Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'Nguyễn Nhật Ánh', 12.50, 'Tiểu thuyết Việt Nam', 'Literature', '978-6041000000', 100),
('Số Đỏ', 'Vũ Trọng Phụng', 8.99, 'Tiểu thuyết cổ điển Việt Nam', 'Literature', '978-6041000001', 80);

-- Sample accounts
INSERT INTO accounts (customer_id, account_number, account_type, balance) VALUES
(1, 'ACC001', 'SAVINGS', 1000.00),
(2, 'ACC002', 'CHECKING', 500.00);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_books_category ON books(category);
CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_cart_customer ON cart_items(customer_id); 
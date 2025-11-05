-- Script tạo dữ liệu demo cho hệ thống tra cứu hóa đơn

-- Tạo bảng users nếu chưa có
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100)
);

-- Tạo bảng customers nếu chưa có
CREATE TABLE IF NOT EXISTS customers (
    id INT PRIMARY KEY,
    phone VARCHAR(20),
    address VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Tạo bảng orders nếu chưa có
CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Tạo bảng products nếu chưa có
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    unit VARCHAR(20)
);

-- Tạo bảng order_items nếu chưa có
CREATE TABLE IF NOT EXISTS order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    book_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (book_id) REFERENCES products(id)
);

-- Insert dữ liệu demo
-- User khách hàng
INSERT INTO users (username, password, role, name, email) VALUES
('customer1', '123456', 'customer', 'Nguyễn Văn A', 'nguyenvana@email.com');

-- Customer
INSERT INTO customers (user_id, phone, address) VALUES
((SELECT id FROM users WHERE username = 'customer1'), '0912345678', '123 Đường ABC, Quận 1, TP.HCM');

-- Products
INSERT INTO products (name, description, price, unit) VALUES
('Pepsi vị chanh 0 calo', 'Nước ngọt Pepsi không calo vị chanh', 10000, 'Chai'),
('Monster Mango Loco vị trái cây', 'Nước tăng lực Monster vị xoài', 24000, 'Lon'),
('Snack Oishi vị hành', 'Snack khoai tây Oishi vị hành', 5000, 'Gói');

-- Orders cho Nguyễn Văn A
INSERT INTO orders (customer_id, order_date, total_amount, status) VALUES
((SELECT id FROM customers WHERE id = (SELECT id FROM users WHERE username = 'customer1')),
 '2025-09-02', 98000, 'delivered'),
((SELECT id FROM customers WHERE id = (SELECT id FROM users WHERE username = 'customer1')),
 '2025-09-03', 240000, 'delivered'),
((SELECT id FROM customers WHERE id = (SELECT id FROM users WHERE username = 'customer1')),
 '2025-09-05', 157000, 'pending');

-- Order items cho hóa đơn 45 (giả sử id = 1)
INSERT INTO order_items (order_id, book_id, quantity, unit_price, total_price) VALUES
(1, 1, 4, 10000, 40000),
(1, 2, 2, 24000, 48000),
(1, 3, 2, 5000, 10000);

-- Order items cho hóa đơn 48 (giả sử id = 2)
INSERT INTO order_items (order_id, book_id, quantity, unit_price, total_price) VALUES
(2, 2, 10, 24000, 240000);

-- Order items cho hóa đơn 53 (giả sử id = 3)
INSERT INTO order_items (order_id, book_id, quantity, unit_price, total_price) VALUES
(3, 1, 10, 10000, 100000),
(3, 3, 10, 5000, 50000),
(3, 2, 1, 7000, 7000);


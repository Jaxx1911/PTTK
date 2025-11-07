-- Script tạo dữ liệu demo cho hệ thống tra cứu hóa đơn

-- Tạo bảng users nếu chưa có
CREATE TABLE IF NOT EXISTS user (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
    );

-- Tạo bảng customers nếu chưa có
CREATE TABLE IF NOT EXISTS customer (
                                        id INT PRIMARY KEY,
                                        phone VARCHAR(20),
    address VARCHAR(255),
    FOREIGN KEY (id) REFERENCES user(id)
    );

-- Tạo bảng orders nếu chưa có
CREATE TABLE IF NOT EXISTS invoice (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       customer_id INT NOT NULL,
                                       sale_staff_id INT,
                                       delivery_staff_id INT,
                                       date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                       total_amount FLOAT(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (sale_staff_id) REFERENCES user(id),
    FOREIGN KEY (delivery_staff_id) REFERENCES user(id)
    );

-- Tạo bảng products nếu chưa có
CREATE TABLE IF NOT EXISTS product (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(100) NOT NULL,
    description TEXT,
    price FLOAT(10),
    unit VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS invoice_product (
                                               invoice_id INT,
                                               product_id INT,
                                               quantity FLOAT(10) NOT NULL,
    unit_price FLOAT(10) NOT NULL,
    sub_total FLOAT(10) NOT NULL,

    PRIMARY KEY (invoice_id, product_id)
    );

CREATE TABLE IF NOT EXISTS supplier (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS import_invoice (
                                              id INT PRIMARY KEY AUTO_INCREMENT,
                                              supplier_id INT NOT NULL,
                                              manager_id INT NOT NULL,
                                              import_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                              total_amount FLOAT(10),
                                              total_quantity FLOAT(10),
    );

CREATE TABLE IF NOT EXISTS import_invoice_product (
                                                      import_invoice_id INT,
                                                      product_id INT ,
                                                      quantity FLOAT(10),
    unit_import_price FLOAT(10),
    sub_total FLOAT(10),

    PRIMARY KEY (import_invoice_id, product_id)
    );
-- Tạo bảng order_items nếu chưa có


-- Insert dữ liệu demo
-- User khách hàng
INSERT INTO user (username, password, role, name, email) VALUES
    ('customer1', '123456', 'customer', 'Nguyễn Văn A', 'nguyenvana@email.com');

-- Customer
INSERT INTO customer (id, phone, address) VALUES
    ((SELECT id FROM user WHERE username = 'customer1'), '0912345678', '123 Đường ABC, Quận 1, TP.HCM');

-- Products
INSERT INTO product (name, description, price, unit) VALUES
                                                         ('Pepsi vị chanh 0 calo', 'Nước ngọt Pepsi không calo vị chanh', 10000, 'Chai'),
                                                         ('Monster Mango Loco vị trái cây', 'Nước tăng lực Monster vị xoài', 24000, 'Lon'),
                                                         ('Snack Oishi vị hành', 'Snack khoai tây Oishi vị hành', 5000, 'Gói');

-- Thêm nhiều sản phẩm hơn
INSERT INTO product (name, description, price, unit) VALUES
                                                         ('Coca Cola Zero 320ml', 'Nước ngọt Coca Cola không đường', 8000, 'Chai'),
                                                         ('7Up Mojito 330ml', 'Nước ngọt 7Up vị Mojito', 9000, 'Lon'),
                                                         ('Sting Dâu 330ml', 'Nước tăng lực Sting vị dâu', 10000, 'Chai'),
                                                         ('Red Bull 250ml', 'Nước tăng lực Red Bull', 15000, 'Lon'),
                                                         ('Aquafina 500ml', 'Nước tinh khiết Aquafina', 5000, 'Chai'),
                                                         ('Lavie 1.5L', 'Nước khoáng Lavie', 8000, 'Chai'),
                                                         ('Snack Poca vị kim chi', 'Snack khoai tây Poca vị kim chi', 6000, 'Gói'),
                                                         ('Bánh Oreo sô-cô-la', 'Bánh quy Oreo nhân kem sô-cô-la', 12000, 'Gói'),
                                                         ('Kẹo Alpenliebe vị sữa', 'Kẹo cứng Alpenliebe vị sữa', 15000, 'Gói'),
                                                         ('Mì Hảo Hảo tôm chua cay', 'Mì ăn liền Hảo Hảo', 4000, 'Gói'),
                                                         ('Trà xanh 0 độ 350ml', 'Trà xanh không độ Coca Cola', 8000, 'Chai'),
                                                         ('Snack Swing khoai tây', 'Snack khoai tây chiên Swing', 7000, 'Gói');

-- Thêm nhân viên bán hàng và giao hàng
INSERT INTO user (username, password, role, name, email) VALUES
                                                             ('sale01', 'sale123', 'sale', 'Trần Thị B', 'tranthib@company.com'),
                                                             ('sale02', 'sale123', 'sale', 'Lê Văn C', 'levanc@company.com'),
                                                             ('delivery01', 'delivery123', 'delivery', 'Phạm Văn D', 'phamvand@company.com'),
                                                             ('delivery02', 'delivery123', 'delivery', 'Hoàng Thị E', 'hoangthie@company.com'),
                                                             ('manager01', 'manager123', 'manager', 'Vũ Văn F', 'vuvanf@company.com');

-- Thêm nhà cung cấp
INSERT INTO supplier (name, address, phone, email) VALUES
                                                       ('Công ty TNHH Nước giải khát ABC', '456 Đường XYZ, Quận 3, TP.HCM', '0283456789', 'abc@supplier.com'),
                                                       ('Công ty CP Thực phẩm DEF', '789 Đường LMN, Quận Bình Thạnh, TP.HCM', '0287654321', 'def@supplier.com'),
                                                       ('Nhà phân phối GHI', '321 Đường PQR, Quận Tân Bình, TP.HCM', '0289876543', 'ghi@supplier.com');

-- Thêm hóa đơn nhập hàng
INSERT INTO import_invoice (supplier_id, manager_id, import_date, total_amount) VALUES
                                                                                    (1, (SELECT id FROM user WHERE username = 'manager01'), '2024-10-01 08:30:00', 15000000),
                                                                                    (2, (SELECT id FROM user WHERE username = 'manager01'), '2024-10-15 10:00:00', 8500000),
                                                                                    (3, (SELECT id FROM user WHERE username = 'manager01'), '2024-10-20 14:30:00', 12000000);

-- Thêm chi tiết hóa đơn nhập hàng
INSERT INTO import_invoice_product (import_invoice_id, product_id, quantity, unit_import_price, sub_total) VALUES
                                                                                                               -- Hóa đơn nhập 1
                                                                                                               (1, 1, 200, 7000, 1400000),
                                                                                                               (1, 2, 150, 18000, 2700000),
                                                                                                               (1, 4, 300, 6000, 1800000),
                                                                                                               (1, 5, 250, 7000, 1750000),
                                                                                                               (1, 7, 400, 11000, 4400000),
                                                                                                               (1, 8, 180, 3500, 630000),

                                                                                                               -- Hóa đơn nhập 2
                                                                                                               (2, 3, 500, 3500, 1750000),
                                                                                                               (2, 6, 200, 5000, 1000000),
                                                                                                               (2, 10, 350, 8500, 2975000),
                                                                                                               (2, 13, 400, 4000, 1600000),

                                                                                                               -- Hóa đơn nhập 3
                                                                                                               (3, 9, 150, 3500, 525000),
                                                                                                               (3, 11, 300, 11000, 3300000),
                                                                                                               (3, 12, 250, 6000, 1500000),
                                                                                                               (3, 14, 600, 5000, 3000000);

-- Thêm hóa đơn bán hàng
INSERT INTO invoice (customer_id, sale_staff_id, delivery_staff_id, date, total_amount, status) VALUES
                                                                                                    ((SELECT id FROM user WHERE username = 'customer1'),
                                                                                                     (SELECT id FROM user WHERE username = 'sale01'),
                                                                                                     (SELECT id FROM user WHERE username = 'delivery01'),
                                                                                                     '2024-10-25 09:15:00', 195000, 'completed'),

                                                                                                    ((SELECT id FROM user WHERE username = 'customer1'),
                                                                                                     (SELECT id FROM user WHERE username = 'sale02'),
                                                                                                     (SELECT id FROM user WHERE username = 'delivery01'),
                                                                                                     '2024-10-28 14:30:00', 87000, 'completed'),

                                                                                                    ((SELECT id FROM user WHERE username = 'customer1'),
                                                                                                     (SELECT id FROM user WHERE username = 'sale01'),
                                                                                                     (SELECT id FROM user WHERE username = 'delivery02'),
                                                                                                     '2024-11-01 10:00:00', 156000, 'processing'),

                                                                                                    ((SELECT id FROM user WHERE username = 'customer1'),
                                                                                                     (SELECT id FROM user WHERE username = 'sale02'),
                                                                                                     NULL,
                                                                                                     '2024-11-01 16:45:00', 45000, 'pending');

-- Thêm chi tiết hóa đơn bán hàng
INSERT INTO invoice_product (invoice_id, product_id, quantity, unit_price, sub_total) VALUES
                                                                                          -- Hóa đơn 1
                                                                                          (1, 1, 5, 10000, 50000),
                                                                                          (1, 2, 3, 24000, 72000),
                                                                                          (1, 3, 5, 5000, 25000),
                                                                                          (1, 7, 2, 15000, 30000),
                                                                                          (1, 11, 3, 6000, 18000),

                                                                                          -- Hóa đơn 2
                                                                                          (2, 4, 4, 8000, 32000),
                                                                                          (2, 8, 2, 5000, 10000),
                                                                                          (2, 13, 3, 4000, 12000),
                                                                                          (2, 14, 5, 5000, 25000),
                                                                                          (2, 10, 2, 4000, 8000),

                                                                                          -- Hóa đơn 3
                                                                                          (3, 2, 2, 24000, 48000),
                                                                                          (3, 5, 6, 10000, 60000),
                                                                                          (3, 9, 4, 12000, 48000),

                                                                                          -- Hóa đơn 4
                                                                                          (4, 6, 3, 9000, 27000),
                                                                                          (4, 11, 3, 6000, 18000);

-- Kiểm tra dữ liệu đã insert
SELECT 'Products Count:' as Info, COUNT(*) as Total FROM product
UNION ALL
SELECT 'Users Count:', COUNT(*) FROM user
UNION ALL
SELECT 'Suppliers Count:', COUNT(*) FROM supplier
UNION ALL
SELECT 'Import Invoices Count:', COUNT(*) FROM import_invoice
UNION ALL
SELECT 'Sales Invoices Count:', COUNT(*) FROM invoice;
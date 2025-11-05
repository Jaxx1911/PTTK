# Module Tra Cứu Hoá Đơn

## Tổng quan
Module tra cứu hoá đơn cho phép khách hàng xem danh sách và chi tiết các đơn hàng của mình.

## Các file đã tạo

### 1. Model Classes
- **Invoice.java** - Model chứa thông tin hoá đơn (id, mã hoá đơn, ngày, tổng tiền, trạng thái, khách hàng, danh sách sản phẩm)
- **InvoiceProduct.java** - Model chứa thông tin sản phẩm trong hoá đơn (id, sản phẩm, số lượng, đơn giá, thành tiền)

### 2. Data Access Object
- **InvoiceDAO.java** - Xử lý truy vấn database với các phương thức:
  - `getInvoicesByCustomerId(int customerId)` - Lấy danh sách hoá đơn của khách hàng
  - `getInvoiceById(int invoiceId)` - Lấy chi tiết một hoá đơn
  - `getInvoiceByIdAndCustomerId(int invoiceId, int customerId)` - Lấy hoá đơn và kiểm tra quyền truy cập
  - `getCustomerIdByUserId(int userId)` - Chuyển đổi user ID sang customer ID
  - `getInvoiceProducts(int invoiceId)` - Lấy danh sách sản phẩm trong hoá đơn

### 3. Servlet
- **InvoiceServlet.java** - Xử lý HTTP requests với mapping `/invoice`
  - GET request không có action: Hiển thị danh sách hoá đơn
  - GET request với action=detail&invoiceId=X: Hiển thị chi tiết hoá đơn

### 4. JSP Views
- **SearchInvoice.jsp** - Trang hiển thị danh sách hoá đơn với:
  - Table hiển thị các hoá đơn
  - Format ngày giờ (dd/MM/yyyy HH:mm)
  - Format tiền tệ (VND)
  - Trạng thái với màu sắc khác nhau
  - Link xem chi tiết

- **InvoiceDetail.jsp** - Trang hiển thị chi tiết hoá đơn với:
  - Thông tin hoá đơn (mã, ngày, khách hàng, trạng thái)
  - Bảng danh sách sản phẩm
  - Tổng tiền
  - Link quay lại danh sách

### 5. Database Schema Updates
- Đã thêm dữ liệu mẫu vào `database_schema.sql`:
  - 5 đơn hàng mẫu với các trạng thái khác nhau
  - 8 order items (sản phẩm trong các đơn hàng)

## Cách sử dụng

### 1. Cài đặt Database
```bash
# Chạy script SQL để tạo database và dữ liệu mẫu
mysql -u root -p < src/main/resources/database_schema.sql
```

### 2. Build và Deploy
```bash
# Build project
mvn clean package

# Deploy file WAR vào Tomcat
cp target/pttk-bookstore-1.0.0.war $TOMCAT_HOME/webapps/
```

### 3. Truy cập Module
1. Đăng nhập với tài khoản khách hàng:
   - Username: `john_doe` hoặc `jane_smith`
   - Password: `password` (mặc định trong schema)

2. Truy cập trang tra cứu hoá đơn:
   ```
   http://localhost:8080/pttk-bookstore-1.0.0/invoice
   ```

3. Xem chi tiết hoá đơn:
   ```
   http://localhost:8080/pttk-bookstore-1.0.0/invoice?action=detail&invoiceId=1
   ```

## Tính năng

### 1. Danh sách hoá đơn
- Hiển thị tất cả hoá đơn của khách hàng đang đăng nhập
- Sắp xếp theo ngày đặt hàng (mới nhất trước)
- Hiển thị: Mã hoá đơn, Ngày đặt, Tổng tiền, Trạng thái
- Màu sắc trạng thái:
  - **PENDING** (Chờ xử lý): Vàng
  - **CONFIRMED** (Đã xác nhận): Xanh dương
  - **SHIPPED** (Đang giao): Tím
  - **DELIVERED** (Đã giao): Xanh lá
  - **CANCELLED** (Đã hủy): Đỏ

### 2. Chi tiết hoá đơn
- Hiển thị thông tin hoá đơn đầy đủ
- Danh sách sản phẩm với số lượng, đơn giá, thành tiền
- Tính tổng tiền tự động
- Kiểm tra quyền truy cập (chỉ khách hàng sở hữu mới xem được)

### 3. Bảo mật
- Yêu cầu đăng nhập
- Kiểm tra session
- Chỉ hiển thị hoá đơn của khách hàng đang đăng nhập
- Không cho phép xem hoá đơn của người khác

## Dữ liệu mẫu

### Khách hàng 1 (john_doe - Customer ID: 1)
- 3 đơn hàng: DELIVERED, SHIPPED, CONFIRMED

### Khách hàng 2 (jane_smith - Customer ID: 2)
- 2 đơn hàng: DELIVERED, PENDING

## Công nghệ sử dụng
- Java Servlet API
- JDBC
- JSP + JSTL
- MySQL Database
- HTML5 + CSS3

## Ghi chú
- Module này tích hợp với hệ thống user/customer hiện có
- Sử dụng DatabaseConnection utility class để kết nối database
- Responsive design với CSS tùy chỉnh
- Format tiền tệ và ngày tháng theo chuẩn Việt Nam


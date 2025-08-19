# PTTK Bookstore Web Application

A comprehensive Java web application built with JSP, Servlet, JDBC, MySQL, and DAO patterns that demonstrates user management, book inventory management, and e-commerce functionality.

## 🎯 Project Overview

This project fulfills three main exercises:

1. **Exercise 1**: User registration and authentication system with login/logout functionality
2. **Exercise 2**: Complete book management system with CRUD operations 
3. **Exercise 3**: Extended bookstore system with customer management, orders, and full e-commerce capabilities

## 🏗️ Architecture

The application follows the **MVC (Model-View-Controller)** pattern and implements the **DAO (Data Access Object)** pattern for clean separation of concerns:

- **Model**: Entity classes representing database tables
- **View**: JSP pages for user interface
- **Controller**: Servlets handling HTTP requests and business logic
- **DAO**: Data access layer for database operations

## 🛠️ Technology Stack

- **Backend**: Java 11, Servlets 4.0, JSP 2.3
- **Database**: MySQL 8.0 with connection pooling
- **Frontend**: Bootstrap 5.1.3, Font Awesome 6.0, HTML5, CSS3, JavaScript
- **Build Tool**: Apache Maven 3.8+
- **Security**: BCrypt password hashing
- **Application Server**: Apache Tomcat 9.0+

## 📋 Features

### Exercise 1: User Management
- ✅ User registration with validation
- ✅ Secure login/logout with session management
- ✅ Password hashing using BCrypt
- ✅ Form validation (client-side and server-side)
- ✅ Responsive design with Bootstrap

### Exercise 2: Book Management (CRUD)
- ✅ List all books with pagination-ready interface
- ✅ Add new books with comprehensive validation
- ✅ Edit existing book information
- ✅ Delete books with confirmation
- ✅ Search books by title, author, or category
- ✅ Filter books by category
- ✅ Stock quantity management
- ✅ ISBN uniqueness validation

### Exercise 3: Complete Bookstore System
- ✅ Customer profile management
- ✅ Address management for shipping
- ✅ Account management system
- ✅ Order processing workflow
- ✅ Shopping cart functionality (database schema ready)
- ✅ Order history and tracking
- ✅ Comprehensive database design with relationships

## 🗄️ Database Schema

The application uses a well-designed MySQL database with the following main tables:

- `users` - User authentication and basic information
- `customers` - Extended customer profiles linked to users
- `addresses` - Customer addresses for shipping
- `accounts` - Customer account management
- `books` - Book inventory with categorization
- `orders` - Order management and tracking
- `order_items` - Individual items within orders
- `cart_items` - Shopping cart functionality

## 🏁 Quick Start

### Prerequisites

- Java 11 or higher
- Apache Maven 3.6+
- MySQL 8.0+
- Apache Tomcat 9.0+

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd PTTK
   ```

2. **Set up the database**
   ```bash
   mysql -u root -p
   source src/main/resources/database_schema.sql
   ```

3. **Configure database connection**
   
   Update the database connection settings in:
   `src/main/java/com/bookstore/util/DatabaseConnection.java`
   ```java
   dataSource.setUrl("jdbc:mysql://localhost:3306/bookstore_db?useSSL=false&serverTimezone=UTC");
   dataSource.setUsername("your_username");
   dataSource.setPassword("your_password");
   ```

4. **Build the project**
   ```bash
   mvn clean compile
   ```

5. **Deploy to Tomcat**
   ```bash
   mvn clean package
   # Copy target/pttk-bookstore-1.0.0.war to Tomcat webapps directory
   ```

6. **Access the application**
   
   Open your browser and navigate to: `http://localhost:8080/pttk-bookstore-1.0.0/`

## 🧪 Demo Credentials

The application comes with pre-configured demo data:

**Admin User:**
- Username: `admin`
- Password: `password`

**Regular Users:**
- Username: `john_doe` | Password: `password`
- Username: `jane_smith` | Password: `password`

## 🎨 User Interface

The application features a modern, responsive design with:

- Clean and intuitive navigation
- Mobile-friendly responsive layout
- Professional color scheme
- Interactive forms with real-time validation
- Success/error message notifications
- Consistent styling across all pages

## 📁 Project Structure

```
PTTK/
├── pom.xml                          # Maven configuration
├── README.md                        # Project documentation
└── src/
    ├── main/
    │   ├── java/com/bookstore/
    │   │   ├── model/               # Entity classes
    │   │   │   ├── User.java
    │   │   │   ├── Book.java
    │   │   │   ├── Customer.java
    │   │   │   ├── Address.java
    │   │   │   ├── Account.java
    │   │   │   ├── Order.java
    │   │   │   └── OrderItem.java
    │   │   ├── dao/                 # Data Access Objects
    │   │   │   ├── UserDAO.java
    │   │   │   ├── BookDAO.java
    │   │   │   └── CustomerDAO.java
    │   │   ├── servlet/             # Controllers
    │   │   │   ├── UserRegistrationServlet.java
    │   │   │   ├── UserLoginServlet.java
    │   │   │   ├── UserLogoutServlet.java
    │   │   │   ├── BookServlet.java
    │   │   │   └── DashboardServlet.java
    │   │   └── util/                # Utilities
    │   │       └── DatabaseConnection.java
    │   ├── resources/
    │   │   └── database_schema.sql  # Database setup script
    │   └── webapp/
    │       ├── WEB-INF/
    │       │   └── web.xml          # Web application configuration
    │       ├── css/
    │       │   └── style.css        # Custom styles
    │       ├── index.jsp            # Home page
    │       ├── login.jsp            # Login form
    │       ├── register.jsp         # Registration form
    │       ├── dashboard.jsp        # User dashboard
    │       ├── book-list.jsp        # Book listing and search
    │       └── book-form.jsp        # Add/edit book form
```

## 🔄 Class Diagram

The application implements a comprehensive object-oriented design with proper relationships between entities. The class diagram shows the relationships between User, Customer, Address, Account, Book, Order, and OrderItem classes, along with their respective DAO classes.

## 🚀 Advanced Features

### Security Features
- Password hashing with BCrypt
- Session management with timeout
- SQL injection prevention with prepared statements
- Input validation and sanitization
- CSRF protection considerations

### Database Features
- Connection pooling for performance
- Transaction management
- Foreign key constraints
- Indexes for optimized queries
- Cascading deletes where appropriate

### UI/UX Features
- Responsive design for all devices
- Loading states and user feedback
- Form validation with real-time feedback
- Consistent navigation and branding
- Accessibility considerations

## 🧪 Testing the Application

### Manual Testing Scenarios

1. **User Registration**
   - Test with valid data
   - Test with duplicate username/email
   - Test password confirmation mismatch
   - Test field validation

2. **User Authentication**
   - Test login with valid credentials
   - Test login with invalid credentials
   - Test session persistence
   - Test logout functionality

3. **Book Management**
   - Add books with various data combinations
   - Edit existing books
   - Delete books (with confirmation)
   - Search and filter functionality
   - ISBN uniqueness validation

## 🔧 Configuration

### Database Configuration
The database connection settings can be modified in `DatabaseConnection.java`:
- URL, username, and password
- Connection pool settings
- Timeout configurations

### Application Configuration
Web application settings in `web.xml`:
- Session timeout (default: 30 minutes)
- Error page mappings
- Security constraints

## 🚀 Deployment

### Development Deployment
```bash
mvn clean package
cp target/pttk-bookstore-1.0.0.war $TOMCAT_HOME/webapps/
```

### Production Considerations
- Configure proper database credentials
- Set up SSL/TLS encryption
- Configure proper logging
- Set up monitoring and health checks
- Configure backup strategies

## 🤝 Contributing

This project is part of an academic exercise. For contributions:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📝 License

This project is created for educational purposes as part of the PTTK coursework.

## 📚 References

The project implementation follows best practices from:

1. [JSP Servlet JDBC MySQL CRUD Tutorial](https://www.javaguides.net/2019/03/jsp-servlet-jdbc-mysql-crud-example-tutorial.html)
2. [Registration Form Tutorial](https://www.javaguides.net/2019/03/registration-form-using-jsp-servlet-jdbc-mysql-example.html)
3. [Login Form Tutorial](https://www.javaguides.net/2019/03/login-form-using-jsp-servlet-jdbc-mysql-example.html)
4. [CodeJava CRUD Example](https://www.codejava.net/coding/jsp-servlet-jdbc-mysql-create-read-update-delete-crud-example)

## 🐛 Known Issues

- Advanced order processing workflow is database-ready but not fully implemented in the UI
- Shopping cart functionality is prepared in the database schema but requires additional servlet implementation
- Email verification for registration is not implemented
- Advanced search filters could be enhanced

## 🔮 Future Enhancements

- Complete shopping cart implementation
- Order processing and payment integration
- Email notifications
- Admin panel for user management
- Inventory management alerts
- Reporting and analytics dashboard
- API endpoints for mobile app integration

---

**Built with ❤️ for PTTK coursework** 

For questions or support, please refer to the course materials or contact the instructor. 
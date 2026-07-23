# 🔐 Authentication System

A secure authentication system built with **Spring Boot** and **Spring Security** that provides user registration, login, JWT-based authentication, and role-based authorization.

## 🚀 Features

* User Registration
* Secure Login Authentication
* JWT (JSON Web Token) Authentication
* Password Encryption using BCrypt
* Role-Based Authorization (USER / ADMIN)
* Spring Security Integration
* RESTful APIs
* MySQL Database Integration
* DTO-Based Request & Response Handling
* Global Exception Handling (if implemented)

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT
* MySQL
* ModelMapper
* Maven
* Lombok

---

## 📂 Project Structure

```text
src
├── Controller
├── DTO
├── Entity
├── Repository
├── Security
├── Services
├── playLoad
├── Config
└── Exception
```

---

## 🔑 API Endpoints

### Register User

```http
POST /register
```

Request Body

```json
{
  "name": "Rohit Verma",
  "email": "rohit@example.com",
  "password": "Password@123",
  "confirmPassword": "Password@123",
  "phoneNo": "9876543210"
}
```

---

### Login

```http
POST /login
```

Request Body

```json
{
  "email": "rohit@example.com",
  "password": "Password@123"
}
```

Response

```json
{
  "accessToken": "JWT_TOKEN",
  "user": {
    "id": 1,
    "name": "Rohit Verma",
    "email": "rohit@example.com",
    "role": "ROLE_USER"
  }
}
```

---

### Get All Users (Protected)

```http
GET /allUsers
```

Authorization Header

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## ⚙️ Getting Started

### Clone the repository

```bash
git clone https://github.com/Rohit-code07/authentication-system.git
cd authentication-system
```

### Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_system
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

### Run the application

```bash
mvn spring-boot:run
```

---

## 🔒 Authentication Flow

1. User registers with email and password.
2. Password is encrypted using BCrypt before storing in MySQL.
3. User logs in with valid credentials.
4. Spring Security authenticates the user.
5. A JWT token is generated and returned.
6. The client includes the token in the `Authorization` header for protected APIs.
7. JWT is validated on each request before granting access.

---

## 📌 Future Improvements

* Refresh Token Support
* Email Verification
* Forgot Password
* Password Reset
* OAuth2 Login (Google/GitHub)
* Swagger/OpenAPI Documentation
* Docker Support
* Unit & Integration Testing
* Account Lock After Multiple Failed Logins

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push the branch
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

# QuizMaster BackEnd
QuizMaster Backend is a Java-based backend application for the QuizMaster web application. It is built using Java, Spring Boot, Spring Security, MySQL, and utilizes JWT (JSON Web Tokens) for authentication. This application allows users to search for different quiz categories, take quizzes, and retrieve their results.

## Prerequisites
To run QuizMaster Backend locally, you need to have the following installed:

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- Maven

## Technologies
- Java
- Spring Boot
- Spring JPA
- Spring Security
- JWT
- MySQL

## Installation
To install QuizMaster Backend and set it up locally, follow the steps below:
1. Clone the repository
2. Navigate to the project directory
3. Create a application.yml file and add the following variables

```
server:
  port: 8000
  
  servlet:
    context-path: /api
  
spring:
# Database Configuartion
  datasource:
    url: "url"
    username: "sql-username"
    password: "sql-password"
    driver-class-name: com.mysql.cj.jdbc.Driver
    
    # JPA Configuration
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect 
      
#Role Id
role.admin.id: "unique id for admin role"
role.normal.id: "unique id for normal role"      

# JWT Secret Key
application:
  security:
    jwt:
     secret-key: "secret-key"
     expiration: "jwt-token-expiry-time"
     refresh-token:
      expiration: "refresh-token-expiry-time"
``` 

## Security
QuizMaster Backend implements role-based authentication using JWT (JSON Web Tokens). Each request to protected endpoints should include an Authorization header with the value Bearer <JWT_TOKEN>.
## Database
QuizMaster Backend uses MySQL for data storage. It is configured to automatically create and update the database schema based on the entity classes. Ensure that the configured MySQL server is running and accessible with the provided credentials.
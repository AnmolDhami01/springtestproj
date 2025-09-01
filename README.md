# Spring Cloud Microservices with Eureka-Gateway

This project consists of two microservices:

1. **Eureka-Gateway** (Port 8761) - Combined Eureka Server + API Gateway
2. **Book Service** (Port 8081) - Handles business logic for books, users, and authentication

## Project Structure

```
├── src/                              # Eureka-Gateway Service (Port 8761)
│   ├── main/java/com/newSpring/testApp/
│   │   ├── config/                   # Gateway routing configuration
│   │   └── TestAppApplication.java   # Main app with @EnableEurekaServer
│   └── main/resources/
│       └── application.properties
├── book-service/                     # Book Service Microservice (Port 8081)
│   ├── src/main/java/com/newSpring/bookservice/
│   │   ├── controllers/              # REST controllers
│   │   ├── service/                  # Business logic interfaces
│   │   ├── serviceImpl/              # Business logic implementations
│   │   ├── modal/                    # Data models and repositories
│   │   ├── config/                   # Security and other configurations
│   │   └── BookServiceApplication.java
│   └── src/main/resources/
│       └── application.properties
└── pom.xml                           # Eureka-Gateway dependencies
```

## Prerequisites

- Java 17
- Maven 3.6+
- MySQL 8.0+

## Running the Services

### 1. Start Eureka-Gateway (Combined Service)

```bash
mvnw spring-boot:run
```

The combined service will start on port 8761 and provide:

- **Eureka Dashboard**: `http://localhost:8761`
- **API Gateway**: Routes requests to microservices

### 2. Start Book Service

```bash
cd book-service
mvnw spring-boot:run
```

The service will start on port 8081 and register with Eureka-Gateway.

## API Endpoints

All requests go through the Eureka-Gateway at `http://localhost:8761`:

- **Books API**: `http://localhost:8761/api/books/**`
- **Users API**: `http://localhost:8761/api/users/**`
- **Auth API**: `http://localhost:8761/api/auth/**`

The gateway automatically routes these requests to the book-service microservice.

## Service Discovery

- **Eureka-Gateway**: `http://localhost:8761` (Dashboard + Gateway)
- **Book Service**: `http://localhost:8081`

## Database Configuration

Update the database configuration in `book-service/src/main/resources/application.properties`:

- Database URL
- Username
- Password

## Notes

- **Eureka-Gateway** combines Eureka Server and API Gateway in one application
- **Book Service** contains all business logic, security, and data access
- The gateway handles routing, CORS, and load balancing
- Book service registers with Eureka-Gateway for service discovery
- Single application to manage instead of separate Eureka and Gateway

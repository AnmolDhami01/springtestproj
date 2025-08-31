# Spring Cloud Microservices with API Gateway

This project consists of three microservices:

1. **Eureka Server** (Port 8761) - Service discovery and registration
2. **API Gateway** (Port 8080) - Routes requests to appropriate microservices
3. **Book Service** (Port 8081) - Handles business logic for books, users, and authentication

## Project Structure

```
├── eureka-server/                    # Eureka Server (Port 8761)
│   ├── src/main/java/com/newSpring/eurekaserver/
│   │   └── EurekaServerApplication.java
│   └── src/main/resources/
│       └── application.properties
├── src/                              # API Gateway Service (Port 8080)
│   ├── main/java/com/newSpring/testApp/
│   │   ├── config/                   # Gateway routing configuration only
│   │   └── TestAppApplication.java
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
└── pom.xml                           # Gateway dependencies only
```

## Prerequisites

- Java 17
- Maven 3.6+
- MySQL 8.0+

## Running the Services

### Option 1: With Eureka Server (Recommended for Production)

#### 1. Start Eureka Server

```bash
cd eureka-server
mvnw spring-boot:run
```

The Eureka server will start on port 8761. Visit `http://localhost:8761` to see the dashboard.

#### 2. Start Book Service

```bash
cd book-service
mvnw spring-boot:run
```

The service will start on port 8081 and register with Eureka.

#### 3. Start API Gateway

```bash
mvnw spring-boot:run
```

The gateway will start on port 8080 and register with Eureka.

### Option 2: Without Eureka (Simpler for Development)

If you don't want to run Eureka server, you can disable it:

1. **Edit** `src/main/resources/application.properties`
2. **Uncomment** this line: `# eureka.client.enabled=false`
3. **Comment out** the Eureka client configuration

Then start only the Book Service and API Gateway.

## API Endpoints

All requests go through the API Gateway at `http://localhost:8080`:

- **Books API**: `http://localhost:8080/api/books/**`
- **Users API**: `http://localhost:8080/api/users/**`
- **Auth API**: `http://localhost:8080/api/auth/**`

The gateway automatically routes these requests to the book-service microservice.

## Service Discovery

- **Eureka Server**: `http://localhost:8761` (if running)
- **API Gateway**: `http://localhost:8080`
- **Book Service**: `http://localhost:8081`

## Database Configuration

Update the database configuration in `book-service/src/main/resources/application.properties`:

- Database URL
- Username
- Password

## Notes

- **Eureka Server** provides service discovery and registration
- **API Gateway** is clean and minimal - only contains routing logic
- **Book Service** contains all business logic, security, and data access
- The gateway handles routing, CORS, and load balancing
- Both services register with Eureka for service discovery (if enabled)
- No unnecessary business logic code in the gateway project

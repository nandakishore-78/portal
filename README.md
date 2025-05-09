# Course Portal Microservices Application

This is a microservices-based web application that provides online course management functionality. The application consists of three main microservices:

1. Course Portal (Main Service)
2. Finance Service
3. Library Service

## Features

- User Registration and Authentication
- Course Management
- Course Enrollment
- Student Profile Management
- Graduation Eligibility Checking
- Integration with Finance and Library Services

## Prerequisites

- Java 17 or higher
- Maven
- Spring Boot 3.2.3
- Docker and Docker Compose
- PostgreSQL (for production)

## Project Structure

```
src/main/java/org/example/portal/
├── config/           # Configuration classes
├── controller/       # REST controllers
├── dto/             # Data Transfer Objects
├── model/           # Entity classes
├── repository/      # JPA repositories
├── security/        # Security configuration
└── service/         # Business logic and external service integration
```

## API Endpoints

### Authentication
- POST `/api/auth/signup` - Register a new user
- POST `/api/auth/signin` - Login and get JWT token

### Courses
- GET `/api/courses` - Get all courses
- GET `/api/courses/{id}` - Get course by ID
- POST `/api/courses/{courseId}/enroll` - Enroll in a course
- GET `/api/courses/enrolled` - Get enrolled courses

### User Profile
- GET `/api/users/profile` - Get user profile
- PUT `/api/users/profile` - Update user profile
- GET `/api/users/graduation-eligibility` - Check graduation eligibility

## Setup and Running

### Local Development

1. Clone the repository
2. Configure the application.yml file with your settings
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Docker Setup

1. Make sure Docker and Docker Compose are installed on your system
2. Clone the repository
3. Make the initialization script executable:
   ```bash
   chmod +x init-multiple-dbs.sh
   ```
4. Build and start the containers:
   ```bash
   docker-compose up -d
   ```

The application will be available at:
- Course Portal: http://localhost:8080
- Eureka Server: http://localhost:8761
- Finance Service: http://localhost:8081
- Library Service: http://localhost:8082

To stop the containers:
```bash
docker-compose down
```

To view logs:
```bash
docker-compose logs -f
```

## Microservices Integration

The application integrates with two other microservices:

### Finance Service
- Handles payment processing
- Manages invoices
- Checks for outstanding payments

### Library Service
- Manages course materials
- Tracks book loans
- Checks for overdue books

## Security

The application uses JWT (JSON Web Tokens) for authentication. All endpoints except `/api/auth/**` and `/api/courses` require authentication.

## Database

- Development: H2 database (in-memory)
- Production: PostgreSQL

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request 
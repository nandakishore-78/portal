# Course Portal

A JWT-secured REST API microservice for managing student course enrolments, user profiles, and graduation eligibility. Built as part of the Software Engineering for Service Computing module at Leeds Beckett University.

Unlike a traditional monolithic student system, this service acts purely as a stateless API gateway — it handles authentication and orchestrates calls to the Finance and Library microservices without owning any business logic that lives downstream. It runs on H2 in development and PostgreSQL in production, and supports Eureka service discovery for containerised deployments.

## Features

- User registration and JWT-based login
- Role-based access control (student / admin)
- Course listing and enrolment
- View enrolled courses and personal profile
- Profile updates (name, contact info)
- Graduation eligibility check — calls Finance service to confirm no outstanding invoices
- Eureka service registry integration for dynamic service discovery

## Tech stack

- Java 17
- Spring Boot 3.2
- Maven
- PostgreSQL (production) / H2 (development)
- Spring Security + JWT
- Docker / Docker Compose
- Spring Cloud Eureka Client
- RestTemplate (inter-service calls)

## Running with Docker Compose

```bash
docker-compose up
```

Starts the portal on port `8080` alongside a PostgreSQL instance. The Finance and Library services need to be running separately for integration features (enrolment invoicing, graduation check) to work.

## Running locally (development)

No database setup needed — H2 runs in-memory.

```bash
mvn spring-boot:run
```

The H2 console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:portaldb`).

## Configuration

For production, set the following environment variables or update `application-prod.yml`:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>
SPRING_DATASOURCE_USERNAME=<user>
SPRING_DATASOURCE_PASSWORD=<password>
JWT_SECRET=<secret-key-min-32-characters>
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://<eureka-host>:8761/eureka/
```

The development profile (`application.yml`) uses H2 and a placeholder JWT secret — do not use these in production.

## API reference

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/signup` | Register a new user |
| POST | `/api/auth/signin` | Login — returns JWT token |

### Courses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/courses` | List all available courses (public) |
| GET | `/api/courses/{id}` | Get course details |
| POST | `/api/courses/{courseId}/enroll` | Enrol in a course (authenticated) |
| GET | `/api/courses/enrolled` | View your enrolled courses |

### User profile

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/profile` | Get your profile |
| PUT | `/api/users/profile` | Update your profile |
| GET | `/api/users/graduation-eligibility` | Check graduation eligibility |

All endpoints except `/api/auth/**` and `GET /api/courses` require a valid JWT in the `Authorization: Bearer <token>` header.

## Architecture

```
Client  ──►  Course Portal (this)
                 ├──►  Finance Service  (invoice creation on enrolment, balance check for graduation)
                 └──►  Library Service  (student account registration)
```

The portal calls the Finance service when:
- A student enrols in a course — an invoice is raised for the enrolment fee
- A student checks graduation eligibility — their account balance is checked

Eureka is used for service discovery in containerised deployments, so Finance and Library service URLs are resolved dynamically rather than hardcoded.

## Security design

JWT tokens are generated on signin and validated on every protected request via `JwtAuthenticationFilter`. Tokens include the user's role and expire after 24 hours (`86400000ms`). The secret key is injected at runtime via environment variable — nothing sensitive lives in the codebase.

## Module

Software Engineering for Service Computing — Leeds Beckett University

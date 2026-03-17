# MaintAIn-Vacation-BE

Vacation Management Backend — Java + Spring Boot REST API with HTTP Basic Auth.

## Prerequisites

- Java 17+
- Maven 3.9+

## Running

```bash
mvn spring-boot:run
```

The server starts on **http://localhost:8080**.

## Hardcoded Users

| Username | Password  | Vacation Days |
|----------|-----------|---------------|
| admin    | admin123  | 25            |
| user     | user123   | 20            |

## API Endpoints

All endpoints require HTTP Basic Auth (`Authorization: Basic <base64(username:password)>`).

| Method | Endpoint              | Description              |
|--------|-----------------------|--------------------------|
| GET    | `/api/users/me`       | Get current user profile |
| GET    | `/api/vacations`      | List all vacations       |
| POST   | `/api/vacations`      | Create vacation request  |
| DELETE | `/api/vacations/{id}` | Delete a vacation        |

## Tech Stack

- Java 17, Spring Boot 3.2
- Spring Security (Basic Auth, stateless)
- Spring Data JPA + H2 (in-memory)
- Jakarta Validation

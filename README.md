# ShopSphere — Product Service

A Spring Boot microservice for managing product catalog information in the ShopSphere e-commerce system. It provides REST APIs for creating, retrieving, updating, and deleting products, with PostgreSQL persistence, request validation, automated tests, and Docker support.

## Features

- REST APIs for product CRUD operations
- Spring Boot and Java 17
- PostgreSQL database integration using Spring Data JPA
- Request validation for product fields
- Centralized exception handling
- Unit and controller tests
- Docker and Docker Compose support
- GitHub Actions CI for automated testing, application packaging, and Docker image building

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 4
- **Build tool:** Maven
- **Database:** PostgreSQL 16
- **Persistence:** Spring Data JPA / Hibernate
- **Testing:** JUnit and Spring Boot test support
- **Containerization:** Docker, Docker Compose
- **CI:** GitHub Actions

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/products` | Create a product |
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get a product by ID |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |

### Example: Create a Product

**Request**

`POST /api/products`

Header: `Content-Type: application/json`

```json
{
  "name": "Wireless Mouse",
  "description": "Wireless computer mouse",
  "price": 499.00
}
```

A successful creation returns HTTP `201 Created`.

Other expected responses include `200 OK` for successful retrieval or update, `204 No Content` for successful deletion, `400 Bad Request` for invalid input, and `404 Not Found` when a product does not exist.

## Run Locally

### Prerequisites

- Java 17
- Docker Desktop with Docker Compose
- Git

### Configuration

The application reads its database connection settings from environment variables when running in Docker Compose.

Create a `.env` file in the project root:

```dotenv
DB_PASSWORD=your_local_database_password
```

Do not commit `.env` or real credentials to Git.

### Start the application and database

From the project root, run:

```bash
docker compose up -d --build
```

Check the running containers:

```bash
docker ps
```

The API should be available at:

`http://localhost:8080/api/products`

The PostgreSQL container is exposed on host port `5433`; the application connects to PostgreSQL internally through the Compose service name.

### Stop the containers

```bash
docker compose down
```

Product data is stored in a named Docker volume so it can persist when containers are restarted or recreated. Avoid `docker compose down -v` unless you intentionally want to remove that volume and its data.

## Run Tests

Using the Maven Wrapper:

```bash
./mvnw clean test
```

On Windows Command Prompt:

```bat
mvnw.cmd clean test
```

Tests that load the full application context require a reachable PostgreSQL database and appropriate datasource environment variables.

## CI Pipeline

GitHub Actions runs on pushes and pull requests targeting `main`. The workflow:

1. Sets up Java 17.
2. Starts PostgreSQL for the test run.
3. Executes automated tests.
4. Packages the Spring Boot application.
5. Builds the Docker image.

## Project Structure

```text
src/
  main/
    java/com/shopsphere/product/
      controller/
      dto/
      entity/
      exception/
      repository/
      service/
    resources/
  test/
Dockerfile
compose.yaml
.dockerignore
.github/
  workflows/
    ci.yml
pom.xml
README.md
```

## Scope

This service owns product catalog information, including product names, descriptions, and prices. Inventory quantities and stock reservations belong to the separate Inventory Service planned for the ShopSphere microservices system.

# E-Commerce REST API

> A REST API for managing products, categories, and images for an ecommerce platform, built with Spring Boot and MySQL.

---

## Tech Stack

| Technology | Details |
|------------|---------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.4 |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA |
| Build Tool | Maven |
| Containerization | Docker Compose |

---

## Prerequisites

- Java 21+
- Maven 3.6+
- MySQL 8.0 or Docker

---

## Setup

**1. Start the database**

    docker-compose up -d

**2. Run the application**

    mvn spring-boot:run

**3. Access the API**

    http://localhost:9191

---

## API Endpoints

All routes are prefixed with `/api/v1`

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/products/all` | Get all products |
| GET | `/products/product/{id}/product` | Get product by ID |
| POST | `/products/add` | Add a product |
| PUT | `/products/product/{id}/update` | Update a product |
| DELETE | `/products/product/{id}/delete` | Delete a product |
| GET | `/products/by/brand-and-name?brandName=&productName=` | Filter by brand and name |
| GET | `/products/product/by-brand` | Filter by brand |
| GET | `/products/product/by-name` | Filter by name |
| GET | `/products/product/{category}/all/products` | Get products by category |

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categories/all` | Get all categories |
| POST | `/categories/add` | Add a category |
| GET | `/categories/category/{id}/category` | Get category by ID |
| PUT | `/categories/category/{id}/update` | Update a category |
| DELETE | `/categories/category/{id}/delete` | Delete a category |

### Images

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/images/upload` | Upload images for a product (multipart: `files`, `productId`) |
| GET | `/images/image/download/{imageId}` | Download an image |
| PUT | `/images/image/{imageId}/update` | Replace an image |
| DELETE | `/images/image/{imageId}/delete` | Delete an image |

---

## Response Format

All endpoints return a consistent response envelope:

    {
      "message": "...",
      "data": {}
    }

---

## Error Handling

| Status Code | Meaning |
|-------------|---------|
| 404 | Resource not found |
| 409 | Resource already exists |
| 500 | Internal server error |

---

## CI/CD

This project uses **GitHub Actions** for continuous integration. The pipeline runs automatically on every push or pull request to the `main` branch and performs the following steps:

1. Checks out the code
2. Sets up the JDK
3. Builds the project with Maven
4. Runs the test suite

This ensures every change is built and tested before being merged.

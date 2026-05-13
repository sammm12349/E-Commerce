# E-Commerce REST API

> A REST API for managing products, categories, images, and a full shopping cart workflow for an e-commerce platform, built with Spring Boot and MySQL.

---

## Tech Stack

| Technology | Details |
|------------|---------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.4 |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA / Hibernate |
| Mapping | MapStruct |
| Build Tool | Maven |
| Containerization | Docker Compose |

---

## Features

- **Product Management** — full CRUD with filtering by brand, category, and name
- **Category Management** — organize products into categories
- **Image Upload** — attach multiple images to products with download support
- **Shopping Cart** — create carts, add/remove/update items, calculate totals
- **DTO Layer** — clean separation between persistence entities and API responses
- **Centralized Error Responses** — consistent JSON envelope via `ApiResponse`
- **Custom Exceptions** — `ResourceNotFoundException`, `AlreadyExistsException`

---

## Project Structure

```
src/main/java/com/spear/e_commerce/
├── Controller/        # REST endpoints
├── service/           # Business logic
├── repository/        # Spring Data JPA repositories
├── model/             # JPA entities (Product, Cart, CartItem, Category, Image)
├── dto/               # Data Transfer Objects
├── Mapper/            # MapStruct mappers
├── request/           # Request payload classes
├── Response/          # Response wrapper (ApiResponse)
└── exceptions/        # Custom exception classes
```

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

### Cart

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/carts/{cartId}/my-cart` | Get cart details |
| DELETE | `/carts/{cartId}/clear` | Clear all items from a cart |
| GET | `/carts/{cartId}/cart/total-price` | Get the total price of the cart |

### Cart Items

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/cartItems/item/add?cartId=&productId=&quantity=` | Add an item to a cart |
| DELETE | `/cartItems/{cartId}/item/{itemId}/remove` | Remove an item from a cart |
| PUT | `/cartItems/cart/{cartId}/item/{itemId}/update?quantity=` | Update the quantity of an item |

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

## Example Requests

**Add an item to a cart:**

    curl -X POST "http://localhost:9191/api/v1/cartItems/item/add?cartId=1&productId=1&quantity=2"

**Get a cart:**

    curl http://localhost:9191/api/v1/carts/1/my-cart

**Get cart total:**

    curl http://localhost:9191/api/v1/carts/1/cart/total-price

---

## CI/CD

This project uses **GitHub Actions** for continuous integration. The pipeline runs automatically on every push or pull request to the `main` branch and performs the following steps:

1. Checks out the code
2. Sets up the JDK
3. Builds the project with Maven
4. Runs the test suite

This ensures every change is built and tested before being merged.

---

## Roadmap

- [ ] JWT authentication and Spring Security
- [ ] User accounts with role-based access (Customer / Admin)
- [ ] Order and OrderItem entities — checkout flow
- [ ] Pagination and sorting on product listings
- [ ] Global exception handling with `@ControllerAdvice`
- [ ] Unit and integration tests
- [ ] Redis caching for product lookups
- [ ] OpenAPI / Swagger documentation

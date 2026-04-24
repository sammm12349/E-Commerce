E-Commerce REST API
A REST API for managing products, categories, and images for an ecommerce platform, built with Spring Boot and MySQL.

Tech Stack
Java 21
Spring Boot 4.0.4
MySQL 8.0
Spring Data JPA
Maven
Docker Compose
Prerequisites
Java 21+
Maven 3.6+
MySQL 8.0 or Docker
Setup
Start the database with Docker: docker-compose up -d
Run the app: mvn spring-boot:run
API is available at http://localhost:9191
Endpoints
All routes are prefixed with /api/v1

Products
GET /products/all — Get all products
GET /products/product/{id}/product — Get product by ID
POST /products/add — Add a product
PUT /products/product/{id}/update — Update a product
DELETE /products/product/{id}/delete — Delete a product
GET /products/by/brand-and-name?brandName=&productName= — Filter by brand and name
GET /products/product/by-brand — Filter by brand
GET /products/product/by-name — Filter by name
GET /products/product/{category}/all/products — Get products by category
Categories
GET /categories/all — Get all categories
POST /categories/add — Add a category
GET /categories/category/{id}/category — Get category by ID
PUT /categories/category/{id}/update — Update a category
DELETE /categories/category/{id}/delete — Delete a category
Images
POST /images/upload — Upload images for a product (multipart: files, productId)
GET /images/image/download/{imageId} — Download an image
PUT /images/image/{imageId}/update — Replace an image
DELETE /images/image/{imageId}/delete — Delete an image
Response Format
All endpoints return:

{ "message": "...", "data": {} }
Error Handling
404 — Resource not found
409 — Resource already exists
500 — Internal server error
CI/CD
This project uses GitHub Actions for continuous integration. The pipeline runs automatically on every push or pull request to the main branch and performs the following steps:

Checks out the code
Sets up JDK
Builds the project with Maven
Runs the test suite

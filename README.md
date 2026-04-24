E-Commerce REST API
A RESTful API for managing an ecommerce platform, built with Spring Boot and MySQL. Supports full CRUD operations for products, categories, and product images.

Tech Stack
- Language: Java 21
- Framework: Spring Boot 4.0.4
- Database: MySQL 8.0
- ORM: Spring Data JPA (Hibernate)
- Build Tool: Maven
- Containerization: Docker Compose (for local MySQL)

Prerequisites
- Java 21+
- Maven 3.6+
- MySQL 8.0 (or Docker for containerized setup)

Getting Started
1. Clone the repository
```bash
git clone <your-repo-url>
cd e-commerce
```

2. Start the database
Option A — Docker (recommended):

```bash
docker-compose up -d
```

This starts MySQL on port `3067` with the `e_commerce_db` database pre-configured.

Option B — Manual MySQL setup:

```sql
CREATE DATABASE e_commerce_db;
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON e_commerce_db.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;
```

3. Configure the application
Edit `src/main/resources/application.properties` if needed:

```properties
server.port=9191

spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce_db
spring.datasource.username=admin
spring.datasource.password=admin123

api.prefix=/api/v1
```

> If using Docker Compose, the datasource port should match the container's mapped port (`3067`).

4. Build and run
```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:9191\`.

API Endpoints
All endpoints are prefixed with `/api/v1`.

Products
Method	Endpoint	Description
`GET`	`/products/all`	Get all products
`GET`	`/products/product/{id}/product`	Get product by ID
`POST`	`/products/add`	Add a new product
`PUT`	`/products/product/{id}/update`	Update a product
`DELETE`	`/products/product/{id}/delete`	Delete a product
`GET`	`/products/by/brand-and-name?brandName=&productName=`	Filter by brand and name
`GET`	`/products/by/category-and-brand`	Filter by category and brand
`GET`	`/products/product/by-name`	Filter by name
`GET`	`/products/product/by-brand`	Filter by brand
`GET`	`/products/product/{category}/all/products`	Get all products in a category
`GET`	`/products/product/count/by-brand/and-name`	Count products by brand and name
Categories
Method	Endpoint	Description
`GET`	`/categories/all`	Get all categories
`POST`	`/categories/add`	Add a new category
`GET`	`/categories/category/{id}/category`	Get category by ID
`GET`	`/categories/category/{name}/category`	Get category by name
`PUT`	`/categories/category/{id}/update`	Update a category
`DELETE`	`/categories/category/{id}/delete`	Delete a category
Images
Method	Endpoint	Description
`POST`	`/images/upload`	Upload images for a product (`multipart/form-data`: `files`, `productId`)
`GET`	`/images/image/download/{imageId}`	Download an image
`PUT`	`/images/image/{imageId}/update`	Replace an image (`multipart/form-data`: `file`)
`DELETE`	`/images/image/{imageId}/delete`	Delete an image
Response Format
All endpoints return a consistent `ApiResponse` envelope:

```json
{
"message": "Item found!",
"data": {}
}
```

Data Models
Product
Field	Type	Description
`id`	Long	Auto-generated primary key
`name`	String	Product name
`brand`	String	Product brand
`price`	BigDecimal	Product price
`inventory`	int	Stock quantity
`description`	String	Product description
`category`	Category	Associated category
`images`	List<Image>	Associated images
Category
Field	Type	Description
`id`	Long	Auto-generated primary key
`name`	String	Category name
`products`	List<Product>	Products in this category
Image
Field	Type	Description
`id`	Long	Auto-generated primary key
`fileName`	String	Original filename
`fileType`	String	MIME type (e.g. `image/jpeg`)
`image`	Blob	Binary image data
`downloadUrl`	String	Download endpoint URL
`product`	Product	Associated product
Project Structure
```
e-commerce/
├── src/main/java/com/spear/e_commerce/
│ ├── Controller/ # REST controllers
│ ├── model/ # JPA entities
│ ├── repository/ # Data access layer
│ ├── service/ # Business logic
│ ├── request/ # Request DTOs
│ ├── dto/ # Response DTOs
│ ├── Response/ # ApiResponse wrapper
│ └── exceptions/ # Custom exceptions
├── src/main/resources/
│ └── application.properties
├── compose.yaml # Docker Compose (MySQL)
└── pom.xml
```

Error Handling
Exception	HTTP Status
`ResourceNotFoundException`	`404 Not Found`
`AlreadyExistsException`	`409 Conflict`
Unhandled exceptions	`500 Internal Server Error`
CI/CD
GitHub Actions is configured to build and test the project on every push or pull request to `main`.

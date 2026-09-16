# Crown Interactive Transaction API


## Introduction

This project is a lightweight RESTful Transaction API built with **Spring Boot** and **PostgreSQL**. It provides endpoints for creating, retrieving, and filtering financial transactions.

The API is designed around a layered architecture that separates HTTP request handling, business logic, data access, and query filtering. It supports transaction filtering by **account number, channel, transaction date range, currency, and status,** as well as pagination for transaction listings.

The implementation also includes request validation, centralized exception handling, database constraints, enum-based state management, and indexed transaction fields to support efficient querying.

---

## Features

- **Transaction Creation:** Create financial transactions with validated transaction details.
- **Transaction Retrieval:** Retrieve an individual transaction using its unique slug.
- **Transaction Listing:** Retrieve transactions with pagination.
- **Dynamic Filtering:** Filter transactions by:
  - Account number
  - Transaction channel
  - Transaction date range
  - Currency
  - Transaction status
- **Pagination:** Supports configurable page number and page size.
- **DTOs:** Request and response DTOs prevent exposing persistence entities directly through the API.
- **Request Validation:** Jakarta Bean Validation is used to validate incoming transaction payloads.
- **Enum-Based State Management:** Transaction type, channel, currency, status, and source are represented using enums.
- **Database Constraints:** Unique constraints and database-level enum checks help maintain data integrity.
- **Database Indexing:** Indexes are configured for frequently filtered transaction fields.
- **Centralized Error Handling:** A global exception handler provides consistent API error responses.
- **Layered Architecture:** Controller, Service, Repository, and Specification layers separate responsibilities.
- **Financial Data Types:** BigDecimal is used for monetary values to avoid floating-point precision issues.
- **Timezone-Aware Timestamps:** Instant is used for transaction timestamps.

---

## Table of Contents

- [Setup](#setup)
- [Prerequisites](#prerequisites)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Environment Variables](#environment-variables)
- [Running the Server](#running-the-server)
- [API Documentation](#api-documentation)
- [API Endpoints](#api-endpoints)
- [Filtering Transactions](#filtering-transactions)
- [Design Decisions](#design-decisions)
- [Trade-offs](#trade-offs)
- [Database Design](#database-design)
- [Error Handling](#error-handling)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [Contact](#contact)

---

## Setup

Follow these instructions to set up the project locally.

- Make sure you have a running PostgreSQL instance.
- Create a PostgreSQL database for the application.
- Configure the database connection using the environment variables described below.
- The API runs on port 8040 by default unless another port is configured.
- The API base path is:

```text
http://localhost:8040/api/v1/
```

## Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- Git


## Tech Stack

- Java 17
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Lombok
- Maven

## Installation


1. Clone the repository:

   ```sh
   git clone https://github.com/peterihimire/c-interactive-assessment.git
   ```

2. Change directory into the project folder:

   ```sh
   cd c-interactive-assessment
   ```

3. Install dependencies:

   ```sh
   ./mvnm clean install
   ```

## Environment Variables

The project requires several environment variables to be configured. Here’s a brief overview of each:

- `DB_URL`jdbc:postgresql://localhost:5432/assessment_db
- `DB_USERNAME`=postgres
- `DB_PASSWORD`=testing123
- `API_PREFIX`=/api/v1
- `SERVER_PORT`=8040
- `SPRING_PROFILES_ACTIVE`=dev
- `FLYWAY_LOG_LEVEL`=DEBUG

Ensure these variables are set in your `.env` file as specified in the [Installation](#installation) section.

## Running the server
Start the application using Maven

   ```sh
   ./mvnw spring-boot:run
   ```
The application will be available at:
```text
http://localhost:8040/api/v1/
```

## API Documention

Postman published documentation of the endpoints [Link](https://documenter.getpostman.com/view/12340633/2sBYB1M7dK)

## API Endpoints
**Create Transaction**
```http
POST /transactions
```
Example Request:

```JSON
{
  "transactionReference": "TXN-20260915-000001",
  "accountNumber": "0123456789",
  "transactionType": "CREDIT",
  "channel": "TRANSFER",
  "amount": 150000.00,
  "currency": "NGN",
  "status": "SUCCESS",
  "transactionDate": "2026-09-15T12:30:00Z",
  "source": "BANK"
}
```

**Get Transaction**
```http
GET /transactions/{slug}
```
Example:

```http
GET /transactions/01k5exampletransaction
```

**Get Transactions**
```http
GET /transactions
```
Default Pagination:

```http
GET /transactions?page=1&limit=10
```

## Filter Transactions
The transaction listing endpoint supports optional filters.

- **Filter by Account Number**

```http
GET /transactions?accountNumber=0123456789
```
- **Filter by Channel**

```http
GET /transactions?channel=POS
```

- **Combine Multiple Filters**

Multiple filters can be combined with pagination:
```http
GET /transactions?page=1&limit=10&accountNumber=0123456789&channel=POS&currency=NGN&status=SUCCESS
```
## Design Decisions
**Layered Architecture**
The application follows a layered architecture:
```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

A separate specification layer is used for dynamic transaction filtering:
```text
Controller
    ↓
Service
    ↓
Specification
    ↓
Repository
    ↓
PostgreSQL
```

**DTOs**
Request and response DTOs are used instead of exposing the JPA entity directly.

For example:

```java
public record CreateTransactionRequest(
    @NotBlank
    String transactionReference,

    @NotBlank
    String accountNumber,

    @NotNull
    TransactionType transactionType,

    @NotNull
    TransactionChannel channel,

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 17, fraction = 2)
    BigDecimal amount,

    CurrencyCode currency,

    @NotNull
    TransactionStatus status,

    @NotNull
    Instant transactionDate,

    @NotNull
    TransactionSource source
) { }

```

```java
public record TransactionResponseDto(
    String slug,
    String transactionReference,
    String accountNumber,
    TransactionType transactionType,
    TransactionChannel channel,
    BigDecimal amount,
    CurrencyCode currency,
    TransactionStatus status,
    Instant transactionDate,
    TransactionSource source
) {}
```

**Dynamic Filtering with specification**

Transaction filtering uses Spring Data JPA Specification and JpaSpecificationExecutor. This avoids creating a large number of repository methods for every possible combination of filters.

For example, instead of having methods such as:
```text
findByStatus()
findByCurrency()
findByStatusAndCurrency()
findByStatusAndChannel()
findByStatusAndCurrencyAndChannel()
...
```
the API dynamically builds a Specification based on the filters supplied by the client.

This allows combinations such as:
```text
accountNumber
+
channel
+
currency
+
status
+
transaction date range
```
without creating separate repository methods for each combination.

**Pagination**
Transaction listing uses Spring Data’s Pageable abstraction.

Example:
```http
GET /transactions?page=1&limit=10
```
**Database Indexing**
Indexes are configured for fields used by the transaction filtering API:
```text
account_number
channel
transaction_date
currency
status
```
A transaction reference and slug are also unique and therefore have unique indexes associated with their constraints.

The indexes are intended to improve lookup and filtering performance as the number of transactions increases.

**Centralized Error Handling**

The application uses @RestControllerAdvice for centralized exception handling.

Examples include:

* ResourceNotFoundException → 404 NOT FOUND
* AlreadyExistsException → 409 CONFLICT
* BadRequestException → 400 BAD REQUEST
* Validation errors → 400 BAD REQUEST
* Unsupported HTTP method → 405 METHOD NOT ALLOWED
* Unexpected server errors → 500 INTERNAL SERVER ERROR

This keeps controllers focused on handling successful requests while allowing errors to be handled consistently across the application.

## Trade-Offs

**Spring Data JPA Specifications**

*Advantage:*

* Supports dynamic combinations of filters.
* Avoids many repository methods.
* Keeps filtering logic separate from the service and controller.

*Trade-off:*

* Adds another abstraction compared with simple Spring Data derived queries.
* Specifications can become more complex for advanced queries.

**Pagination**

*Advantage:*

* Prevents large result sets from being loaded at once.
* Reduces response payload size.
* Provides predictable API responses.

*Trade-off:*

* Clients need to manage page and limit parameters.
* Very large datasets may eventually require more advanced pagination strategies such as cursor/keyset pagination.

**Database Indexes**

*Advantage:*

* Improve filtering and lookup performance.

*Trade-off:*

* Indexes consume additional storage.
* Inserts and updates can become slightly more expensive because indexes must also be maintained.
* Indexes should therefore correspond to actual query patterns rather than being added indiscriminately.

**PostgreSQL**

PostgreSQL was selected because transaction data is highly structured and benefits from:

* ACID transactions
* Strong relational constraints
* Numeric precision
* Indexing
* Mature SQL querying capabilities

The trade-off is that schema changes and highly relational data models require more deliberate database design compared with some schema-flexible databases.

**Manual DTO Mapping**

The current implementation uses explicit DTO conversion.

*Advantage:*

* Simple and easy to understand.
* No additional mapping framework is required.
* Mapping logic is explicit.

*Trade-off:*

* More boilerplate as the number and complexity of DTOs increases.
* A mapping framework such as MapStruct could become useful as the application grows.

*Database Design**

The core transaction table contains:
```text
transactions
├── id
├── slug
├── transaction_reference
├── account_number
├── transaction_type
├── channel
├── amount
├── currency
├── status
├── transaction_date
├── source
├── created_at
└── updated_at
```
Important constraints include:

* id → Primary key
* slug → Unique
* transaction_reference → Unique
* amount → NUMERIC(19,2)
* Required transaction fields → NOT NULL
* Enum fields → PostgreSQL CHECK constraints

**Error Handling**

Example validation error:
```JSON
{
  "status": 400,
  "error": "Bad Request",
  "message": "amount: must be greater than 0",
  "path": "/api/v1/transactions",
  "timestamp": "2026-09-15T12:30:00Z"
}
```
Example resource-not-found response:

```JSON
{
  "status": 404,
  "error": "Not Found",
  "message": "Transaction not found",
  "path": "/api/v1/transactions/invalid-slug",
  "timestamp": "2026-09-15T12:30:00Z"
}
```

## Future Improvements

* Add automated unit and integration tests.
* Add OpenAPI/Swagger documentation.
* Add authentication and authorization if the API is exposed to external clients.
* Add transaction audit/history records.
* Add sorting options to transaction listing.
* Add configurable maximum page size.
* Add database migration management using Flyway or Liquibase instead of relying on Hibernate schema generation.
* Add structured application logging and correlation/request IDs.
* Add Docker and Docker Compose configuration for easier local setup.
* Add CI/CD pipeline for automated testing and deployment.
* Introduce cursor/keyset pagination if transaction volume becomes very large.
* Add monitoring and application metrics using tools such as Micrometer and Prometheus.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch.
3. Make your changes.
4. Add or update tests where applicable.
5. Commit your changes.
6. Push the branch to your fork.
7. Create a pull request.

## Contact

For any questions or support, please reach out to:

* Email: `peterihimire@gmail.com`
* GitHub: Create an issue in the project repository.
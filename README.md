# Crown Interactive Transaction API

A lightweight RESTful Transaction API built with **Spring Boot** and **PostgreSQL**.

The API provides endpoints for creating, retrieving, and filtering financial transactions. It supports pagination, dynamic filtering, request validation, centralized exception handling, database constraints, and indexing for efficient querying.

---

## Features

- Create transactions
- Retrieve a transaction by slug
- List transactions with pagination
- Filter transactions dynamically by:
    - Account number
    - Transaction channel
    - Transaction date range
    - Currency
    - Transaction status
- Request validation using Jakarta Bean Validation
- Enum-based transaction types, channels, currencies, statuses, and sources
- DTO-based API responses
- PostgreSQL database persistence
- Database constraints for data integrity
- Database indexes for frequently queried fields
- Centralized exception handling
- Layered architecture
- `BigDecimal` for monetary values
- `Instant` for transaction timestamps

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

The application uses PostgreSQL as its primary database.

Default API base URL:

```text
http://localhost:8080/api/v1/
```

Postman published documentation of the endpoints
```text
https://documenter.getpostman.com/view/12340633/2sBYB1M7dK
```
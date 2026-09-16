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


















Postman published documentation of the endpoints
```text
https://documenter.getpostman.com/view/12340633/2sBYB1M7dK
```
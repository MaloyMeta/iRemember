# iRemember

### CRM system for managing companies, employees, clients and business operations.

**iRemember** is a backend-focused CRM application built with **Java and Spring Boot**.

The system is designed around a company-oriented architecture where users manage clients, communication, financial operations and other business processes within their company.

The project focuses on building a realistic, maintainable backend rather than a simple CRUD application.

---

## 🧩 Core Concept

The main domain structure is based on the relationship between companies, users and clients:

```text
Company
   │
   ├── Users
   │
   └── Clients
          │
          ├── Comments
          ├── Tags
          ├── Transactions
          └── Delivery information
```

Each company operates within its own isolated data scope.

Users interact with the system according to their roles and permissions.

---

# 🔐 Security

Security is implemented using **Spring Security**.

The application uses authenticated users as the source of authorization context.

Company ownership is taken from the currently authenticated user instead of being provided by the client.

For example, financial and client operations use the authenticated user's company when accessing data:

```text
Authenticated User
        │
        ↓
     Company
        │
        ↓
  Company-owned data
```

This prevents users from simply providing another company's ID to access its data.

Authorization is additionally applied to client operations according to the user's role and relationship with the client.

---

# 👥 Client Management

Clients are one of the main entities of the system.

The application supports operations such as:

- creating clients;
- updating clients;
- deleting clients;
- retrieving clients;
- assigning clients to users;
- managing client status;
- managing client comments;
- working with client tags;
- pagination;
- sorting;
- permission-based access.

Client access is always considered in the context of the user's company.

---

# 💬 Client Information

The CRM stores information related to individual clients instead of keeping business context separated across different systems.

A client can contain related:

- comments;
- tags;
- financial transactions;
- assigned users;
- delivery information;
- business history.

This allows different CRM modules to operate around the same client entity.

---

# 💰 Finance

The finance module is responsible for tracking financial transactions associated with clients and companies.

Each transaction contains information about:

- amount;
- transaction type;
- transaction status;
- description;
- creation date;
- update date;
- client;
- company;
- user who created the transaction.

### Transaction types

```text
INCOME
EXPENSE
```

### Transaction operations

The API supports:

- creating transactions;
- updating transactions;
- deleting transactions;
- retrieving company transactions;
- retrieving client transactions;
- retrieving a specific transaction;
- pagination;
- sorting.

---

## 📊 Financial Statistics

The finance module also provides aggregated information for both clients and companies.

### Client summary

A client financial summary contains:

```text
Client
├── Balance
├── Total Income
├── Total Expense
├── Transaction Count
└── Last Transaction
```

### Company summary

A company financial summary contains:

```text
Company
├── Total Balance
├── Total Income
├── Total Expense
├── Transaction Count
├── Client Count
├── Average Transaction Amount
└── Last Transaction
```

Aggregated financial values are calculated directly through database queries instead of loading all transactions into application memory.

Financial queries are scoped by company to maintain data isolation.

---

# 🚚 Delivery

The CRM is designed to keep delivery information associated with the client.

The intended workflow is:

```text
Client
   │
   └── Delivery
         │
         └── Tracking Number
                │
                ↓
          Delivery Service
                │
                ↓
          Delivery Status
```

This allows employees to access customer and delivery information from the same CRM context.

---

# 🏗️ Architecture

The application follows a layered architecture:

```text
                    REST API
                       │
                       ↓
                 Controllers
                       │
                       ↓
                    Services
                       │
                       ↓
                  Repositories
                       │
                       ↓
                   PostgreSQL
```

The project separates:

- controllers;
- services;
- repositories;
- entities;
- DTOs;
- security;
- exceptions;
- database migrations;
- utility components.

Business logic is kept inside the service layer, while repositories are responsible for persistence and database queries.

---

# 🗄️ Database

The application uses **PostgreSQL** as its relational database.

Database schema changes are managed through **Flyway** migrations.

The project uses **JPA / Hibernate** for persistence.

The database contains relationships between the main business entities and uses foreign keys to maintain referential integrity.

Financial queries also use database indexes for frequently accessed company and client transaction data.

---

# 📦 DTO Architecture

The API does not expose JPA entities directly.

Dedicated DTOs are used for API requests and responses.

For example:

```text
TransactionRequest
TransactionResponse

ClientFinanceSummaryResponse
CompanyFinanceSummaryResponse
LastTransaction
```

This keeps the REST API independent from the internal persistence model and allows response structures to be designed specifically for frontend requirements.

---

# 📄 Pagination & Sorting

Collection endpoints support pagination and sorting through Spring Data's `Pageable`.

Example:

```text
GET /api/finance?page=0&size=20&sortBy=createdAt&direction=desc
```

This allows the frontend to request only the required portion of large datasets.

---

# 🔎 API Structure

The REST API is organized around domain resources.

Examples:

```text
/api/clients
/api/finance
/api/finance/{clientId}
/api/finance/{clientId}/transaction/{transactionId}
```

Financial summary endpoints provide aggregated information for company and client dashboards.

Example:

```text
GET /api/finance/summary
GET /api/finance/{clientId}/summary
```

---

# 🛠️ Technology Stack

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- Lombok
- Jakarta Validation

### Database

- PostgreSQL
- Flyway

### Infrastructure

- Docker
- Docker Compose

### Build & Development

- Maven
- IntelliJ IDEA
- Git
- GitHub

---

# 📂 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com.maloy.iremember/
    │       ├── config/
    │       ├── controllers/
    │       ├── dto/
    │       ├── entity/
    │       ├── enums/
    │       ├── exceptions/
    │       ├── repositories/
    │       ├── security/
    │       ├── services/
    │       └── ...
    │
    └── resources/
        ├── db/
        │   └── migration/
        └── application.properties
```

---

# 🐳 Docker

PostgreSQL is configured to run in Docker for local development.

Start the database with:

```bash
docker compose up -d
```

Check running containers:

```bash
docker ps
```

---

# 🚀 Getting Started

## Requirements

- Java 17+
- Maven
- Docker
- Docker Compose
- Git

## Clone

```bash
git clone https://github.com/MaloyMeta/iRemember.git
cd iRemember
```

## Start PostgreSQL

```bash
docker compose up -d
```

## Run the application

Windows:

```cmd
mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Flyway migrations are applied automatically when the application starts.

---

# 🧠 Engineering Approach

The project is developed with an emphasis on real-world backend engineering practices.

The main focus is on:

- clean separation of responsibilities;
- secure access to company data;
- relational database design;
- transaction management;
- DTO-based API design;
- validation;
- exception handling;
- database migrations;
- efficient database queries;
- pagination;
- authorization;
- maintainable service architecture.

The system is intentionally being built as a realistic CRM backend rather than a minimal demonstration project.

---

# 👨‍💻 Author

**Maloy**

Java Developer focused on backend development with Spring Boot.

GitHub:  
https://github.com/MaloyMeta
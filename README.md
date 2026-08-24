# iRemember

### A scalable CRM platform for managing companies, teams, clients and business operations.

**iRemember** is a business-oriented CRM platform built with **Java and Spring Boot**.

The project started as a personal backend project and is evolving into a full-featured CRM designed around a real-world business model: companies, employees, managers, clients, communication, financial tracking and operational workflows.

The goal is not simply to build another CRUD application.

The goal is to build a **maintainable, secure and extensible business system** that can grow from a small internal tool into a production-ready CRM platform.

> 🚧 **Project Status: Active Development**
>
> The backend is currently the primary focus. A dedicated frontend application is planned as the next major stage of development.

---

## 🎯 Vision

Most small businesses eventually end up spreading their customer information across spreadsheets, messengers, notes, delivery services and separate financial tools.

**iRemember aims to bring these workflows together.**

The long-term vision is a centralized platform where a company can:

- manage its employees and roles;
- manage customers and their lifecycle;
- assign clients to responsible employees;
- track communication and comments;
- organize tags and customer information;
- monitor financial activity;
- track deliveries;
- connect delivery information with customers;
- control access to company data;
- and eventually automate repetitive business operations.

The architecture is being designed with future expansion in mind rather than around a single use case.

---

# ✨ Core Features

## 👥 Company & User Management

The system is designed around a company-based structure.

```text
Company
   │
   └── Users
        │
        └── Clients
```

A company can have multiple users with different responsibilities and access levels.

This provides the foundation for a multi-user CRM rather than a simple personal customer list.

---

## 🔐 Authentication & Authorization

Security is one of the core parts of the system.

The project uses **Spring Security** with JWT-based authentication.

The authorization model is designed around user roles and ownership of business data.

Current role concept:

```text
ADMIN
   │
   ├── Full company access
   │
MANAGER
   │
   ├── Extended access within the company
   │
EMPLOYEE
   │
   └── Access to assigned / permitted clients
```

The authorization layer is being designed so that permissions can become more granular as the system grows.

---

## 👤 Client Management

Clients are the central business entity of iRemember.

The system is designed to support:

- client creation;
- client updates;
- client assignment;
- client status management;
- company ownership;
- responsible manager/employee;
- comments;
- tags;
- contact information;
- future interaction history.

The permission model determines which users can modify particular clients.

For example:

```text
ADMIN
  ↓
Can manage company clients

MANAGER
  ↓
Can manage clients within permitted company scope

EMPLOYEE
  ↓
Can manage assigned clients
and available unassigned clients
```

---

# 💬 Client Communication

The CRM is designed to keep customer-related information attached directly to the client.

Planned communication-related functionality includes:

- comments;
- internal notes;
- interaction history;
- tags;
- important/urgent markers;
- communication timeline.

The idea is to prevent important customer information from becoming lost in external chats or personal notes.

---

# 💰 Financial Management

One of the planned CRM modules is financial tracking.

The long-term goal is to allow businesses to associate financial information with their customers and operations.

Planned functionality includes:

- income tracking;
- expense tracking;
- transaction history;
- client-related financial records;
- financial summaries;
- reporting.

---

# 🚚 Delivery Tracking

iRemember is also planned to integrate delivery workflows.

The concept is to associate a delivery/tracking number with a client and retrieve delivery information through an external delivery service API.

Example workflow:

```text
Client
   │
   └── Delivery
         │
         └── Tracking Number
                │
                ↓
          Delivery API
                │
                ↓
        Delivery Status
```

This allows customer-related delivery information to remain inside the CRM instead of requiring employees to manually switch between systems.

---

# 🏗️ Architecture

The backend follows a layered architecture designed to keep responsibilities separated.

```text
                    ┌─────────────────┐
                    │    Frontend     │
                    │   (Planned)     │
                    └────────┬────────┘
                             │
                             ↓
                    ┌─────────────────┐
                    │   Controllers   │
                    └────────┬────────┘
                             │
                             ↓
                    ┌─────────────────┐
                    │    Services     │
                    └────────┬────────┘
                             │
                             ↓
                    ┌─────────────────┐
                    │  Repositories   │
                    └────────┬────────┘
                             │
                             ↓
                    ┌─────────────────┐
                    │   PostgreSQL    │
                    └─────────────────┘
```

The application separates:

- HTTP/API concerns;
- business logic;
- persistence;
- domain entities;
- security;
- database migrations;
- infrastructure configuration.

This separation makes it possible to evolve individual parts of the system without coupling the entire application together.

---

# 🗄️ Database

The project uses **PostgreSQL** as its primary relational database.

Database schema evolution is handled through **Flyway migrations**.

This allows database changes to be version-controlled together with the application code.

```text
Application
     │
     ↓
Hibernate / JPA
     │
     ↓
PostgreSQL
     ↑
     │
   Flyway
```

The database is currently designed around the core CRM domain:

```text
Company
   │
   └── Users
         │
         └── Clients
                │
                ├── Comments
                ├── Tags
                ├── Financial Data
                └── Deliveries
```

The schema will evolve as new CRM modules are introduced.

---

# 🐳 Docker

The development database is containerized using Docker Compose.

The repository contains the infrastructure configuration required to run PostgreSQL locally.

```bash
docker compose up -d
```

The application itself can then connect to the PostgreSQL container through the configured environment variables.

Sensitive configuration such as:

- database passwords;
- JWT secrets;
- environment-specific credentials;

is intentionally kept outside the repository.

---

# 🛠️ Tech Stack

### Backend

- **Java**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **Hibernate**
- **Spring Security**
- **JWT**
- **Lombok**

### Database

- **PostgreSQL**
- **Flyway**

### Infrastructure

- **Docker**
- **Docker Compose**
- **Maven**

### Planned Frontend

The backend is being developed independently from the frontend.

A dedicated frontend application will be connected to the REST API once the backend domain and security layers are sufficiently mature.

---

# 📂 Project Structure

The project is organized around clear application responsibilities.

```text
src/
└── main/
    ├── java/
    │   └── com.maloy.iRemember/
    │       ├── config/
    │       ├── controller/
    │       ├── dto/
    │       ├── exception/
    │       ├── security/
    │       ├── service/
    │       ├── repository/
    │       └── ...
    │
    └── resources/
        ├── db/
        │   └── migration/
        └── application.properties

compose.yaml
pom.xml
```

The structure will continue evolving as the CRM gains additional modules.

---

# 🔑 Configuration

The application uses environment variables for sensitive configuration.

Create a local `.env` file:

```env
POSTGRES_DB=iremember
POSTGRES_USER=iremember
POSTGRES_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
```

The `.env` file is intentionally excluded from version control.

For other developers, an `.env.example` file can be used as a configuration template.

---

# 🚀 Getting Started

## Requirements

Before running the project, install:

- Java 17+
- Maven
- Docker
- Docker Compose
- Git

---

## 1. Clone the repository

```bash
git clone https://github.com/MaloyMeta/iRemember.git
cd iRemember
```

## 2. Configure environment variables

Create:

```text
.env
```

and configure:

```env
POSTGRES_DB=iremember
POSTGRES_USER=iremember
POSTGRES_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
```

## 3. Start PostgreSQL

```bash
docker compose up -d
```

Verify the container:

```bash
docker ps
```

## 4. Start the application

Using Maven:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

Flyway will apply the available database migrations during application startup.

---

# 🧪 Development Approach

The project is intentionally being developed incrementally.

Instead of implementing every CRM feature immediately, the development process focuses on establishing a stable foundation first:

```text
Domain Model
      ↓
Database
      ↓
Persistence
      ↓
Business Logic
      ↓
Security
      ↓
REST API
      ↓
Frontend
      ↓
Integrations
      ↓
Automation
```

This approach allows new functionality to be built on top of an established architecture instead of accumulating tightly coupled features.

---

# 🗺️ Roadmap

The roadmap represents the long-term direction of iRemember.

## ✅ Completed

- [x] Initial Spring Boot project
- [x] PostgreSQL integration
- [x] Docker Compose development environment
- [x] Flyway database migrations
- [x] Core company domain
- [x] User domain
- [x] Client domain
- [x] Client comments
- [x] Role-based access model
- [x] Basic client authorization rules
- [x] CRUD operations for core CRM entities
- [x] JWT authentication foundation
- [x] Environment-based configuration

---

## 🚧 In Progress

- [ ] Complete Spring Security implementation
- [ ] Refine role-based authorization
- [ ] Improve validation and exception handling
- [ ] Expand client management
- [ ] Improve API structure and DTOs
- [ ] Expand database/domain model
- [ ] Improve test coverage

---

## 📋 Planned

### Authentication & Security

- [ ] User registration
- [ ] Login flow
- [ ] Refresh tokens
- [ ] Password management
- [ ] Account activation
- [ ] Fine-grained permissions
- [ ] Security auditing
- [ ] Session/device management

### CRM

- [ ] Advanced client search
- [ ] Filtering
- [ ] Sorting
- [ ] Pagination
- [ ] Client activity timeline
- [ ] Client tags
- [ ] Client priorities
- [ ] Client statuses
- [ ] Client history
- [ ] Bulk operations

### Company Management

- [ ] Company settings
- [ ] Employee management
- [ ] Role management
- [ ] Permission management
- [ ] Company-level configuration
- [ ] Multi-tenant data isolation

### Communication

- [ ] Internal comments
- [ ] Interaction history
- [ ] Email integration
- [ ] Communication timeline
- [ ] Notifications
- [ ] Reminders

### Finance

- [ ] Income tracking
- [ ] Expense tracking
- [ ] Client-related transactions
- [ ] Financial reports
- [ ] Revenue statistics
- [ ] Monthly/annual summaries

### Delivery

- [ ] Delivery tracking
- [ ] Tracking number management
- [ ] Delivery status synchronization
- [ ] External delivery API integration
- [ ] Delivery history

### Frontend

- [ ] Dedicated frontend application
- [ ] Authentication UI
- [ ] Dashboard
- [ ] Client management interface
- [ ] Company management
- [ ] Employee management
- [ ] Finance dashboard
- [ ] Delivery tracking interface
- [ ] Responsive design
- [ ] Role-based UI

---

# 🚀 Future Vision

The ultimate goal is to turn iRemember into a modular CRM platform rather than a collection of CRUD endpoints.

A possible future architecture:

```text
                         iRemember
                             │
             ┌───────────────┼───────────────┐
             │               │               │
          CRM Core         Finance       Operations
             │               │               │
        ┌────┼────┐       ┌──┴──┐        ┌──┴─────┐
        │    │    │       │     │        │        │
     Clients Users Tags  Income Expenses Delivery Tasks
        │
        └──────────────┐
                       │
                  Communication
                       │
              ┌────────┼────────┐
              │        │        │
            Email    Notes   Notifications
```

As the project grows, additional infrastructure may be introduced:

- Redis
- message brokers
- background jobs
- scheduled tasks
- external integrations
- file storage
- monitoring
- logging
- CI/CD
- automated testing
- cloud deployment

The architecture will be evaluated continuously as complexity increases rather than introducing infrastructure without a concrete requirement.

---

# 📊 Engineering Goals

iRemember is also a learning and engineering project.

The main goal is to gain practical experience with the problems that appear in real backend applications:

- designing relational data models;
- handling complex entity relationships;
- implementing authorization rules;
- maintaining database migrations;
- designing service boundaries;
- validating input;
- handling application errors;
- securing APIs;
- managing configuration and secrets;
- writing maintainable business logic;
- testing business rules;
- integrating external services;
- containerizing infrastructure;
- preparing an application for production.

The project is therefore intentionally larger than a typical CRUD pet project.

---

# 🧠 Why iRemember?

The name **iRemember** represents the main idea behind the application:

> **The CRM should remember the business, so people don't have to.**

Customers, conversations, assignments, financial activity, deliveries, tasks and business history should live in one connected system.

---

# 👨‍💻 Author

**Maloy**

Java Developer focused on backend development with Spring.

This project is being developed as a long-term engineering project and portfolio piece.

### GitHub

[github.com/MaloyMeta](https://github.com/MaloyMeta)

---

# ⭐ Project Status

**iRemember is actively evolving.**

The current implementation represents the foundation of the platform.

The frontend, additional CRM modules, integrations, automation and production infrastructure are part of the long-term roadmap.

> **Built step by step. Designed to grow.**
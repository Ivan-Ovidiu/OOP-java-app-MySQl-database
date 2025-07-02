# FoodGrab

Food delivery application developed in Java ( IntelliJ IDEA ) with MySQL for data storage. Implements OOP principles and provides a complete system for managing orders, restaurants and users.
## OOP Concepts Implemented

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

## Application Architecture

### Model Classes
- **User.java** - Base class for users
- **Customer.java** - Application customers
- **Administrator.java** - System administrators
- **RestaurantOwner.java** - Restaurant owners
- **DeliveryPerson.java** - Delivery couriers
- **Restaurant.java** - Restaurant entities
- **Food.java** - Food products
- **Order.java** - Orders
- **Address.java** - Delivery addresses
- **Card.java** - Payment cards
- **Menu.java** - Restaurant menus

### DAO Layer
- **InterfaceDAO.java** - Common interface for DAO
- **UserDAO.java** - Database operations for users
- **CustomerDAO.java** - Customer specific operations
- **RestaurantDAO.java** - Operations for restaurants
- **FoodDAO.java** - Operations for food products
- **OrderDAO.java** - Operations for orders
- **CardDAO.java** - Operations for cards
- **AuditDAO.java** - Operations for audit

### Service Layer
- **CustomerService.java** - Business logic for customers
- **RestaurantService.java** - Logic for restaurants
- **CardService.java** - Logic for cards

### Main Application
- **Main.java** - Entry point and interactive menu

## Design Patterns

### Singleton Pattern
Service class for the interactive menu implements Singleton to ensure a single instance.

### DAO Pattern
Separation of data access logic into specialized classes for each entity.

## Features

### CRUD Operations
Complete Create, Read, Update, Delete operations for all entities:
- User management
- Restaurant administration
- Order placement and tracking
- Food product management
- Payment card management
### Logging System
The application includes an audit logging system:

- audit.csv - CSV file for logging all user actions and system events
- AuditDAO.java - Handles audit operations and CSV file management
### Prerequisites
- Java JDK 8+
- MySQL Server
- JDBC Driver for MySQL
- IntelliJ IDEA


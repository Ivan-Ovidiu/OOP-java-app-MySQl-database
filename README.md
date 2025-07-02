FoodGrab
Food delivery application developed in Java with MySQL for data storage. Implements OOP principles and provides a complete system for managing orders, restaurants and users.
Description
FoodGrab is a platform that connects customers with restaurants through delivery couriers. The application offers complete functionalities for placing orders, managing restaurants and tracking deliveries.
OOP Concepts Implemented

Encapsulation
Inheritance
Polymorphism
Abstraction

Application Architecture
Model Classes

User.java - Base class for users
Customer.java - Application customers
Administrator.java - System administrators
RestaurantOwner.java - Restaurant owners
DeliveryPerson.java - Delivery couriers
Restaurant.java - Restaurant entities
Food.java - Food products
Order.java - Orders
Address.java - Delivery addresses
Card.java - Payment cards
Menu.java - Restaurant menus

DAO Layer

InterfaceDAO.java - Common interface for DAO
UserDAO.java - Database operations for users
CustomerDAO.java - Customer specific operations
RestaurantDAO.java - Operations for restaurants
FoodDAO.java - Operations for food products
OrderDAO.java - Operations for orders
CardDAO.java - Operations for cards
AuditDAO.java - Operations for audit

Service Layer

CustomerService.java - Business logic for customers
RestaurantService.java - Logic for restaurants
CardService.java - Logic for cards

Main Application

Main.java - Entry point and interactive menu

Design Patterns
Singleton Pattern
Service class for the interactive menu implements Singleton to ensure a single instance.
DAO Pattern
Separation of data access logic into specialized classes for each entity.
Features
CRUD Operations
Complete Create, Read, Update, Delete operations for all entities:

User management
Restaurant administration
Order placement and tracking
Food product management
Payment card management

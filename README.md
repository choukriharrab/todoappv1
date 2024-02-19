# To-Do Application README

This README provides an overview of the structure and functionality of the Demo Application.

## Table of Contents

1. [Overview](#overview)
2. [Entities](#entities)
3. [Data Transfer Objects (DTOs)](#data-transfer-objects-dtos)
4. [Repositories](#repositories)
5. [Services](#services)
6. [Controllers](#controllers)
7. [Exception Handling](#exception-handling)
   
## Overview

The Demo Application is a Spring Boot-based RESTful API that manages tasks and users. It provides endpoints for performing CRUD (Create, Read, Update, Delete) operations on tasks and users. The application architecture follows a typical MVC (Model-View-Controller) pattern.

## Entities

### Task Entity

- Represents a task with attributes such as title, description, status, due date, etc.
- Utilizes JPA annotations for entity mapping.
- Includes validation constraints for ensuring data integrity.

### User Entity

- Represents a user with attributes including first name, last name, email, etc.
- Utilizes JPA annotations for entity mapping.
- Includes validation constraints for ensuring data integrity.

## Data Transfer Objects (DTOs)

### TaskRequestDto

- Data transfer object for creating or updating a task.
- Contains fields corresponding to task attributes.

### TaskResponseDto

- Data transfer object for returning task information in responses.
- Contains fields corresponding to task attributes.

### UserRequestDto

- Data transfer object for creating or updating a user.
- Contains fields corresponding to user attributes.

### UserResponseDto

- Data transfer object for returning user information in responses.
- Contains fields corresponding to user attributes.

## Repositories

### TaskRepo

- Repository interface for performing CRUD operations on tasks.
- Extends JpaRepository for built-in CRUD functionality.

### UserRepo

- Repository interface for performing CRUD operations on users.
- Extends JpaRepository for built-in CRUD functionality.

## Services

### TaskService

- Service interface defining methods for task-related operations.
- Includes methods for retrieving, creating, updating, and deleting tasks.

### TaskServiceImpl

- Service implementation for task-related operations.
- Implements methods defined in TaskService.
- Utilizes TaskRepo for database interaction.
- Includes error handling for various scenarios.

### UserService

- Service interface defining methods for user-related operations.
- Includes methods for retrieving, creating, updating, and deleting users.

### UserServiceImp

- Service implementation for user-related operations.
- Implements methods defined in UserService.
- Utilizes UserRepo for database interaction.
- Includes error handling for various scenarios.

## Controllers

### TaskController

- REST controller for handling task-related HTTP requests.
- Defines endpoints for CRUD operations on tasks.
- Utilizes TaskService for task operations.
- Includes error handling for various scenarios.

### UserController

- REST controller for handling user-related HTTP requests.
- Defines endpoints for CRUD operations on users.
- Utilizes UserService for user operations.
- Includes error handling for various scenarios.

## Exception Handling

- Custom exceptions are defined for specific error scenarios (e.g., TaskNotFound, UserNotFound).
- Exception handling is implemented in controllers to return appropriate error responses.

---

This README provides a high-level overview of the To-Do Application's structure and functionality. For detailed information on specific classes or methods, please refer to the corresponding source code files.

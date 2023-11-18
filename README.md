# ChâTop Back-End Project

![ChâTop Logo](./src/main/resources/static/images/chatop-banner.png)

This Back-End project is dedicated to the online platform that connects potential tenants and property owners for seasonal rentals in ChâTop. It implements user authentication, data storage, and resource access.

## Table of Contents

- [ChâTop Back-End Project](#châtop-back-end-project)
  - [Table of Contents](#table-of-contents)
  - [Prerequisites](#prerequisites)
  - [Configuration](#configuration)
  - [Installation Procedure](#installation-procedure)
  - [Project Structure](#project-structure)
  - [Dependencies](#dependencies)
  - [Authentication and Security](#authentication-and-security)
  - [Image Management](#image-management)
  - [API Documentation](#api-documentation)
  - [Miscellaneous](#miscellaneous)

## Prerequisites

Before you begin, ensure that the following software is installed on your system:

- **Java Development Kit (JDK):** You can download the latest version of the [JDK](https://adoptopenjdk.net/) for your platform

- **Apache Maven:** You'll need [Maven](https://maven.apache.org/) for building and managing the project's dependencies.

- **MySQL:** Install and set up MySQL as the database for the ChâTop Back-End. You can follow the installation steps [here](https://openclassrooms.com/fr/courses/6971126-implementez-vos-bases-de-donnees-relationnelles-avec-sql/7152681-installez-le-sgbd-mysql).

## Configuration

1. Java
**Set up Java Environment Variable:**

Ensure that the Java environment variable is correctly configured on your system. This variable is essential for Java applications to run. You can set it up by following the steps specific to your operating system.

- Windows:

1. Open the System Properties.
2. Click on the `Advanced` tab.
3. Click the `Environment Variables` button.
4. Under `System variables`, scroll down to find the `Path` variable and click `Edit.`
5. Add the path to your JDK's binary directory (e.g., `C:\Program Files\Java\jdk[VERSION]\bin`) to the list of values. Make sure to separate it from other entries with a semicolon.
6. Click `OK` to save your changes and run the following command:

```shell
java -version
```

You should see information about the installed Java version.

2. MySQL Database

Follow these steps to configure MySQL Workbench for your Java application:

Open MySQL Workbench.

Connect to your MySQL Server instance.

Create a new database for your application and add all the tables to your database:

```sql
-- Create the tables
DROP DATABASE IF EXISTS `p3-chatop`;

-- Create the database
CREATE DATABASE `p3-chatop`;

-- Switch to the new database
USE `p3-chatop`;

-- Create the tables
CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `rentals` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `owner_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `surface` INT DEFAULT NULL,
    `price` DECIMAL(10, 2) DEFAULT NULL,
    `picture` VARCHAR(255) DEFAULT NULL,
    `description` TEXT,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `messages` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `rental_id` BIGINT NOT NULL,
    `message` TEXT,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`)
);
```

## Installation Procedure

1. Clone this repository from GitHub: `git clone https://github.com/LePhenix47/Lahouiti_Younes_P3_05112023 .`

2. Configure the database in `application.properties`.

3. Run the application using your IDE or by running `mvn spring-boot:run` in the project directory.

4. Access the Swagger URL to explore and test the API.

5. You can also use Postman to test API calls.

6. Make sure to follow the technical specifications for a successful deployment.

## Project Structure

The project follows a classic layered architecture (Controller/Service/Java Persistence API Repository) to ensure code modularity and maintainability.

## Dependencies

1. Spring Boot Starters:
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-web
- spring-boot-starter-validation

2. Spring Session:

- spring-session-core

3. Database Connector:

- mysql-connector-j (runtime scope)

4. Project Lombok:

- lombok (optional)

5. Testing Dependencies:

- spring-boot-starter-test (test scope)
- spring-security-test (test scope)

6. JSON Web Token (JWT) Dependencies:

- jjwt-api (version: 0.11.2)
- jjwt-impl (version: 0.11.2, runtime scope)
- jjwt-jackson (version: 0.11.2, runtime scope)

## Authentication and Security

Authentication is managed by Spring Security with JWT. All routes require authentication, except those related to account creation or login. Passwords are securely stored in the database. Database credentials are not exposed in the code.

## Image Management

When creating a rental listing, an image is required. This image is sent to the API, stored on the server, and the image URL is saved in the database.

## API Documentation

The API is documented using Swagger. You can access the API documentation by navigating to the Swagger URL.

Although you can view the routes and endpoints here:

- `api/auth`:

| HTTP VERB | Endpoint  | Parameters | Request payload                                                | Response payload                                                                | Description of the response     |
|-----------|-----------|------------|----------------------------------------------------------------|---------------------------------------------------------------------------------|--------------------------------|
| POST      | /register | x          | {     email: string;     name: string;     password: string; } | {     token: string; }                                                          | Object with the JSON Web Token |
| POST      | /login    | x          | {     email: string;     password: string; }                   | {     token: string; }                                                          | Object with the JSON Web Token |
| GET       | /me       | x          | x                                                              | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Info about the user            |

- `api/user`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                | Description of the response |
|-----------|----------|-------------|-----------------|---------------------------------------------------------------------------------|----------------------------|
| GET       | /        | id (number) |        x         | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Info about the user        |

- `api/messages`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                | Description of the response |
|-----------|----------|-------------|-----------------|---------------------------------------------------------------------------------|----------------------------|
| POST       | /        | x |        { rental_id: number, user_id: number, message: string }         | { message: string } | Message about the post request for the rental        |

- `api/rentals`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                                                                                           | Description of the response                |
|-----------|----------|-------------|-----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| GET       | /        | x           | x               | {  id: number,  name: string,  surface: number,  price: number,  picture: string,  description: string,  owner_id: number,  created_at: Date,  updated_at: Date }[] | Array of rentals                          |
| GET       | /        | id (number) | x               | {   id: number,  name: string,  surface: number,  price: number,  picture: string,  description: string,  owner_id: number,  created_at: Date,  updated_at: Date }  | Rental object                             |
| POST      | /        | x           | FormData object | { message: string }                                                                                                                                        | Info about the request to add a rental    |
| PUT       | /        | id (number) | FormData object | { message: string }                                                                                                                                        | Info about the request to modify a rental |

## Miscellaneous

- GitHub Repository: [Link to the Front-End Code](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

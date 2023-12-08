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

1. MySQL Database

Follow these steps to configure MySQL Workbench for your Java application:

Open MySQL Workbench.

Connect to your MySQL Server instance.

Create a new database for your application and add all the tables to your database:

```sql
-- Create the database
DROP DATABASE IF EXISTS `p3-chatop`;

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
    `surface` INT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `picture` TEXT DEFAULT NULL,
    `description` TEXT,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

CREATE TABLE `messages` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `rental_id` BIGINT NOT NULL,
    `message` TEXT,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`) ON DELETE CASCADE
);
```

## Installation Procedure

**Cloning the project:**

1. Clone this repository from GitHub: `git clone https://github.com/LePhenix47/Lahouiti_Younes_P3_05112023 .`

2. Configure the app in `application.properties`.

**Set up the `application.properties` file:**

Once you have cloned the repository, you'll need to add the `application.properties` file on the `src/main/resources/` folder containing these properties:

```properties
# MySQL DB
spring.datasource.url=jdbc:mysql://localhost:3306/P3-Chatop
spring.datasource.username=root
spring.datasource.password=Az&rty1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.sql.init.platform=mysql

# Java Persistence API
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true

# TomCat server
server.port=3001
server.error.include-message=always
server.error.include-binding-errors=always
server.error.include-stacktrace=on_param
server.error.whitelabel.enabled=false

# Swagger 
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
springdoc.swagger-ui.enabled=true


# AWS S3
aws.s3.region=eu-north-1
aws.s3.accessKey=AKIA3PEH7I7P2ZU3DPKR
aws.s3.secretKey=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
aws.s3.bucket-name=oc-p3-chatop

aws.s3.stack.auto=false
aws.s3.credentials.profile-name=lahouiti

# Logger
logging.level.com.amazonaws.util.EC2MetadataUtils=error
logging.level.root=INFO
logging.level.org.springframework=INFO
logging.level.com.yourpackage=DEBUG
logging.level.com.openclassrooms=INFO
logging.level.org.springframework.boot.web.embedded.tomcat=INFO
```

3. Run the application using your IDE or by running `mvn spring-boot:run` in the project directory.

4. Access the Swagger URL to explore and test the API.

5. You can also use Postman to test API calls.

6. Make sure to follow the technical specifications for a successful deployment.

## Project Structure

The project follows a classic layered architecture (Controller/Service/Java Persistence API Repository) to ensure code modularity and maintainability.

## Dependencies

1. **Spring Boot Starters:**
   - `spring-boot-starter-data-jpa`
   - `spring-boot-starter-security`
   - `spring-boot-starter-web`
   - `spring-boot-starter-validation`

2. **Spring Session:**
   - `spring-session-core`

3. **Database Connector:**
   - `mysql-connector-j (runtime scope)`

4. **Project Lombok:**
   - `lombok (optional)`

5. **Testing Dependencies:**
   - `spring-boot-starter-test (test scope)`
   - `spring-security-test (test scope)`

6. **JSON Web Token (JWT) Dependencies:**
   - `jjwt-api (version: 0.11.2)`
   - `jjwt-impl (version: 0.11.2, runtime scope)`
   - `jjwt-jackson (version: 0.11.2, runtime scope)`

7. **MapStruct for DTOs:**
   - `mapstruct (version 1.5.5)`
   - `mapstruct-processor (version 1.5.5)`

8. **Swagger Dependencies:**
   - `springdoc-openapi-starter-webmvc-ui (version: 2.2.0)`
   - `springdoc-openapi-starter-common (version: 2.2.0)`

9. **AWS S3 SDK:**
   - `software.amazon.awssdk:s3:2.21.33`

## Authentication and Security

Authentication is managed by Spring Security with JWT. All routes require authentication, except those related to account creation or login. Passwords are encoded and securely stored in the database. Database credentials are not exposed in the code.

## Image Management

When creating a rental listing, an image is required. This image is sent to the API, stored on the AWS S3 bucket, and the image URL is saved in the database.

## API Documentation

The API is documented using Swagger. You can access the API documentation by navigating to the Swagger URL after running the server `http://localhost:3001/swagger-ui/index.html`.

Although you can view the routes and endpoints here:

- `api/auth`:

| HTTP VERB | Endpoint  | Parameters | Request payload                                                | Response payload                                                                | Description of the response     |
|-----------|-----------|------------|----------------------------------------------------------------|---------------------------------------------------------------------------------|--------------------------------|
| POST      | /register | ×         | {     email: string;     name: string;     password: string; } | {     token: string; }                                                          | Object with the JSON Web Token |
| POST      | /login    | ×         | {     email: string;     password: string; }                   | {     token: string; }                                                          | Object with the JSON Web Token |
| GET       | /me       | ×         | ×                                                             | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Info about the user            |

- `api/user`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                | Description of the response |
|-----------|----------|-------------|-----------------|---------------------------------------------------------------------------------|----------------------------|
| GET       | /        | id (number) |        ×        | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Info about the user        |

- `api/messages`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                | Description of the response |
|-----------|----------|-------------|-----------------|---------------------------------------------------------------------------------|----------------------------|
| POST       | /        | ×|        { rental_id: number, user_id: number, message: string }         | { message: string } | Message about the post request for the rental        |

- `api/rentals`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                                                                                           | Description of the response                |
|-----------|----------|-------------|-----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| GET       | /        | ×          | ×              | {  id: number,  name: string,  surface: number,  price: number,  picture: string,  description: string,  owner_id: number,  created_at: Date,  updated_at: Date }[] | Array of rentals                          |
| GET       | /        | id (number) | ×              | {   id: number,  name: string,  surface: number,  price: number,  picture: string,  description: string,  owner_id: number,  created_at: Date,  updated_at: Date }  | Rental object                             |
| POST      | /        | ×          | FormData object | { message: string }                                                                                                                                        | Info about the request to add a rental    |
| PUT       | /        | id (number) | FormData object | { message: string }                                                                                                                                        | Info about the request to modify a rental |

## Miscellaneous

<details>
  <summary>🔗 Link to Front-End GitHub Repository</summary>
  <a href="https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring" target="_blank">
    Link to the Front-End Code
  </a>
</details>

<details>
  <summary>📊 Check the tables relationships visually</summary>
  <a href="/P3 table relationships.drawio" target="_blank">
    Draw.io file for the table relationships
  </a>
</details>

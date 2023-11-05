# ChâTop Back-End Project

![ChâTop Logo](./src/main/resources/static/images/chatop-banner.png)

This Back-End project is dedicated to the online platform that connects potential tenants and property owners for seasonal rentals in ChâTop. It implements user authentication, data storage, and resource access.

## Table of Contents

- [ChâTop Back-End Project](#châtop-back-end-project)
  - [Table of Contents](#table-of-contents)
  - [Prerequisites](#prerequisites)
  - [Configuration](#configuration)
  - [Project Structure](#project-structure)
  - [Dependencies](#dependencies)
  - [Authentication and Security](#authentication-and-security)
  - [Image Management](#image-management)
  - [API Documentation](#api-documentation)
  - [Installation Procedure](#installation-procedure)
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

## Project Structure

The project follows a classic layered architecture (Controller/Service/Java Persistence API Repository) to ensure code modularity and maintainability.

## Dependencies

- Spring Boot Starter Data JPA
- Spring Boot Starter OAuth2 Authorization Server
- Spring Boot Starter OAuth2 Client
- Spring Boot Starter OAuth2 Resource Server
- Spring Boot Starter Security
- Spring Boot Starter Web
- Spring Session Core
- MySQL Connector/J
- Lombok (optional, for reducing boilerplate code)
- Spring Boot Starter Test (for testing)
- Spring Security Test (for testing)

## Authentication and Security

Authentication is managed by Spring Security with JWT. All routes require authentication, except those related to account creation or login. Passwords are securely stored in the database. Database credentials are not exposed in the code.

## Image Management

When creating a rental listing, an image is required. This image is sent to the API, stored on the server, and the image URL is saved in the database.

## API Documentation

The API is documented using Swagger. You can access the API documentation by navigating to the Swagger URL.

Although you can view the routes and endpoints here:

- `api/auth`:

| HTTP VERB | Endpoint  | Parameters | Request payload                                                | Response payload                                                                | Description of the reponse     |
|-----------|-----------|------------|----------------------------------------------------------------|---------------------------------------------------------------------------------|--------------------------------|
| POST      | /register | x          | {     email: string;     name: string;     password: string; } | {     token: string; }                                                          | Object with the JSON Web Token |
| POST      | /login    | x          | {     email: string;     password: string; }                   | {     token: string; }                                                          | Object with the JSON Web Token |
| GET       | /me       | x          | x                                                              | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Info about the user            |

- `api/user`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                | Description of the reponse |
|-----------|----------|-------------|-----------------|---------------------------------------------------------------------------------|----------------------------|
| GET       | /        | id (number) |        x         | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Info about the user        |

- `api/messages`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                | Description of the reponse |
|-----------|----------|-------------|-----------------|---------------------------------------------------------------------------------|----------------------------|
| POST       | /        | x |        { rental_id: number, user_id: number, message: string }         | {  id: number,  name: string,  email: string,  created_at: Date,  updated_at: Date } | Message about the post request for the rental        |

- `api/rentals`:

| HTTP VERB | Endpoint | Parameters  | Request payload | Response payload                                                                                                                                           | Description of the reponse                |
|-----------|----------|-------------|-----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| GET       | /        | x           | x               | {  id: number,  name: string,  surface: number,  price: number,  picture: string,  description: string,  owner_id: number,  created_at: Date,  updated_at: Date }[] | Array of rentals                          |
| GET       | /        | id (number) | x               | {   id: number,  name: string,  surface: number,  price: number,  picture: string,  description: string,  owner_id: number,  created_at: Date,  updated_at: Date }  | Rental object                             |
| POST      | /        | x           | FormData object | { message: string }                                                                                                                                        | Info about the request to add a rental    |
| PUT       | /        | id (number) | FormData object | { message: string }                                                                                                                                        | Info about the request to modify a rental |

## Installation Procedure

1. Clone this repository from GitHub: `git clone https://github.com/LePhenix47/Lahouiti_Younes_P3_05112023 .`

2. Configure the database in `application.properties`.

3. Run the application using your IDE or by running `mvn spring-boot:run` in the project directory.

4. Access the Swagger URL to explore and test the API.

5. You can also use Postman to test API calls.

6. Make sure to follow the technical specifications for a successful deployment.

## Miscellaneous

- GitHub Repository: [Link to the Front-End Code](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

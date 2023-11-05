# ChâTop Back-End Project

![ChâTop Logo](./src/main/resources/static/images/chatop-banner.png)

This Back-End project is dedicated to the online platform that connects potential tenants and property owners for seasonal rentals in ChâTop. It implements user authentication, data storage, and resource access.

## Project Structure

The project follows a classic layered architecture (Controller/Service/JPA Repository) to ensure code modularity and maintainability.

## Dependencies

- Java 11, 17 or higher
- Spring Boot
- Spring Security with JWT-based authentication
- Spring Data JPA
- Spring Web
- MySQL Driver
- Lombok (optional, for reducing boilerplate code)

## Configuration

The main configuration file is `application.properties`, where you can define database configuration, server port, and other properties.

## Authentication and Security

Authentication is managed by Spring Security with JWT. All routes require authentication, except those related to account creation or login. Passwords are securely stored in the database. Database credentials are not exposed in the code.

## Image Management

When creating a rental listing, an image is required. This image is sent to the API, stored on the server, and the image URL is saved in the database.

## API Documentation

The API is documented using Swagger. You can access the API documentation by navigating to the Swagger URL.

## Installation Procedure

1. Clone this repository from GitHub.

2. Configure the database in `application.properties`.

3. Run the application using your IDE or by running `mvn spring-boot:run` in the project directory.

4. Access the Swagger URL to explore and test the API.

5. You can also use Postman to test API calls.

6. Make sure to follow the technical specifications for a successful deployment.

## Deliverables

- GitHub Repository: [Link to the Back-End Code](link_to_github)
- Detailed Documentation:
  - Step-by-step procedure for installing and running the project.
  - Database installation procedure.
  - Swagger URL for API documentation.

Enjoy developing the ChâTop Back-End!

Marco
Tech Lead
ChâTop

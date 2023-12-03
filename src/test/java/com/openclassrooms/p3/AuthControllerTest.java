package com.openclassrooms.p3;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.p3.payload.request.AuthLoginRequest;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.utils.JwtUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a test class for the AuthController class. It contains several
 * test methods that simulate HTTP requests to the controller endpoints and
 * verify the expected responses.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    /*
     * /register
     */

    /**
     * HAPPY PATH:
     * Test method for registering a new user with valid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRegisterNewUserWithValidInput() throws Exception {
        // Create a valid registration request
        AuthRegisterRequest validRequest = new AuthRegisterRequest(
                "test@example.com",
                "Valid Name",
                "p@ssword123");

        // Convert the validRequest object to JSON
        String validRequestJson = objectMapper.writeValueAsString(validRequest);

        // Perform the registration validRequest
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    /**
     * EDGE CASE:
     * Test method for registering an already registered user with valid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAlreadyRegisteredUserWithValidInput() throws Exception {
        // Same user as before
        AuthRegisterRequest validRequest = new AuthRegisterRequest(
                "test@example.com",
                "Valid Name",
                "p@ssword123");

        // Convert the validRequest object to JSON
        String validRequestJson = objectMapper.writeValueAsString(validRequest);

        // Perform the registration validRequest
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestJson))
                .andExpect(status().isConflict());
    }

    /**
     * EDGE CASE:
     * Test method for registering a new user with invalid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRegisterNewUserWithInvalidInput() throws Exception {
        AuthRegisterRequest invalidRequest = new AuthRegisterRequest(
                "@invalid.com",
                "1nvalid Name",
                "bruh");

        String invalidRequestJson = objectMapper.writeValueAsString(invalidRequest);

        // Perform the registration validRequest
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson))
                .andExpect(status().isBadRequest());
    }

    /*
     * /login
     */

    /**
     * HAPPY PATH:
     * Test method for logging in an existing user with valid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testLoginExistingUserWithValidInput() throws Exception {
        // Create a valid login request
        AuthLoginRequest validRequest = new AuthLoginRequest(
                "test@example.com",
                "p@ssword123");

        // Convert the validRequest object to JSON
        String validRequestJson = objectMapper.writeValueAsString(validRequest);

        // Perform the login request
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    /**
     * EDGE CASE:
     * Test method for logging in an existing user with invalid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testLoginExistingUserWithInvalidInput() throws Exception {
        // Create an invalid login request
        AuthLoginRequest invalidRequest = new AuthLoginRequest(
                "test@example.com",
                "invalidpassword");

        // Convert the invalidRequest object to JSON
        String invalidRequestJson = objectMapper.writeValueAsString(invalidRequest);

        // Perform the login request
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson))
                .andExpect(status().isUnauthorized());
    }

    /*
     * /me
     */

    /**
     * HAPPY PATH:
     * Test method for retrieving authenticated user info with valid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrieveAuthenticatedUserInfoWithValidInput() throws Exception {
        // Mock a JWT token with the desired claims
        Map<String, Long> claims = new HashMap<>();
        claims.put("userId", 1L); // Replace with the desired user ID

        String mockJwt = JwtUtil.generateJwtToken(claims.get("userId"));

        // Perform the request to retrieve authenticated user info
        mvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer " + mockJwt)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.notNullValue()));
    }

    /**
     * EDGE CASE:
     * Test method for retrieving authenticated user info with invalid input.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrieveAuthenticatedUserInfoWithInvalidInput() throws Exception {
        String invalidJwt = "jwt";
        mvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer " + invalidJwt))
                .andExpect(status().isUnauthorized());
    }
}

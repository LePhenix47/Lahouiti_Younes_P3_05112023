package com.openclassrooms.p3;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.p3.payload.request.AuthLoginRequest;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.utils.JwtUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testRetrieveAuthenticatedUserInfoWithInvalidInput() throws Exception {
        String invalidJwt = "jwt";
        mvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer " + invalidJwt))
                .andExpect(status().isUnauthorized());
    }
}

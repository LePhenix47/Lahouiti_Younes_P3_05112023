package com.openclassrooms.p3.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.p3.utils.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * HAPPY PATH:
     * Test method for retrieving a user with a valid user ID and authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testValidUserIdAndAuthorizationHeader() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();

        mvc.perform(get("/api/user/{id}", 1)
                .header("Authorization", "Bearer " + validMockJwt).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    /**
     * EDGE CASE:
     * Test method for retrieving a user with an invalid authorization header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testWithInvalidAuthorizationHeader() throws Exception {
        mvc.perform(get("/api/user/{id}", 1)
                .header("Authorization", "Bearer INVALID_TOKEN"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * EDGE CASE:
     * Test method for retrieving a non-existing user with a valid authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testWithNonExistingUserId() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();

        mvc.perform(get("/api/user/{id}", 420)
                .header("Authorization", "Bearer " + validMockJwt))
                .andExpect(status().isNotFound());
    }

    /**
     * Generates a mock JWT token with a user ID of 1 in the claim.
     *
     * @return A mock JWT token.
     */
    private String getMockJwtWithIdOfOne() {
        // Mock a JWT token with the desired claims
        Map<String, Long> claims = new HashMap<>();
        claims.put("userId", 1L); // Replace with the desired user ID

        return JwtUtil.generateJwtToken(claims.get("userId"));
    }
}
package com.openclassrooms.p3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.p3.utils.JwtUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class RentalControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * HAPPY PATH:
     * Test method for retrieving all rentals with valid authorization header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrievingAllRentals() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();

        mvc.perform(get("/api/rentals")
                .header("Authorization", "Bearer " + validMockJwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentals").isArray());
    }

    /**
     * EDGE CASE:
     * Test method for retrieving all rentals with an invalid authorization header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrievingAllRentalsWithInvalidAuthorizationHeader() throws Exception {
        mvc.perform(get("/api/rentals")
                .header("Authorization", "Bearer INVALID_TOKEN"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * HAPPY PATH:
     * Test method for retrieving a specific rental by ID with valid authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrievingSpecificRentalById() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(get("/api/rentals/{id}", 1)
                .header("Authorization", "Bearer " + validMockJwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    /**
     * EDGE CASE:
     * Test method for retrieving a specific rental with an invalid authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrievingSpecificRentalWithInvalidAuthorizationHeader() throws Exception {
        mvc.perform(get("/api/rentals/{id}", 1)
                .header("Authorization", "Bearer INVALID_TOKEN"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * EDGE CASE:
     * Test method for retrieving a specific rental with an invalid authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRetrievingSpecificNonExistingRental() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(get("/api/rentals/{id}", 420)
                .header("Authorization", "Bearer " + validMockJwt))
                .andExpect(status().isNotFound());
    }

    /**
     * HAPPY PATH:
     * Test method for adding a new rental with valid parameters and authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddingNewRentalWithValidParameters() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();

        mvc.perform(post("/api/rentals")
                .header("Authorization", "Bearer " + validMockJwt)
                .param("name", "TEST Rental")
                .param("surface", "100")
                .param("price", "500.00")
                .param("description", "A new rental property (added through the test class)"))
                .andExpect(status().isCreated());
    }

    /**
     * EDGE CASE:
     * Test method for adding a new rental without a valid JWT in the authorization
     * header
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddingNewRentalWithoutValidAuthorizationHeader() throws Exception {
        mvc.perform(post("/api/rentals")
                .header("Authorization", "Bearer INVALID_TOKEN")
                .param("name", "TEST Rental")
                .param("surface", "100")
                .param("price", "500.00")
                .param("description", "A new rental property (added through the test class)"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * EDGE CASE:
     * Test method for adding a new rental with invalid parameters and valid
     * authorization header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddingNewRentalWithInvalidParameters() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(post("/api/rentals")
                .header("Authorization", "Bearer " + validMockJwt)
                // Has no name
                .param("surface", "100")
                .param("price", "500.00")
                .param("description", "A new rental property"))
                .andExpect(status().isBadRequest());
    }

    /**
     * HAPPY PATH:
     * Test method for updating a rental with valid parameters and authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdatingRentalWithValidParameters() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(put("/api/rentals/{id}", 1)
                .header("Authorization", "Bearer " + validMockJwt)
                .param("name", "Updated Rental")
                .param("surface", "150")
                .param("price", "700.00")
                .param("description", "Updated description"))
                .andExpect(status().isCreated());
    }

    /**
     * EDGE CASE:
     * Test method for updating a rental without a valid authorization header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdatingRentalWithoutValidAuthorizationHeader() throws Exception {
        mvc.perform(put("/api/rentals/{id}", 1)
                .header("Authorization", "Bearer INVALID_TOKEN")
                .param("name", "Updated Rental")
                .param("surface", "150")
                .param("price", "700.00")
                .param("description", "Updated description"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * EDGE CASE:
     * Test method for updating a rental with invalid parameters and authorization
     * header.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdatingRentalWithInvalidParameters() throws Exception {
        String validMockJwt = getMockJwtWithIdOfOne();

        mvc.perform(put("/api/rentals/{id}", 1)
                .header("Authorization", "Bearer " + validMockJwt)
                .param("surface", "150")
                .param("price", "700.00")
                .param("description", "Updated description"))
                .andExpect(status().isBadRequest());
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

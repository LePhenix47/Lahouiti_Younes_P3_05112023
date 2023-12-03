package com.openclassrooms.p3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.openclassrooms.p3.payload.request.MessageRequest;
import com.openclassrooms.p3.utils.JwtUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * HAPPY PATH:
     * Test case for successfully creating a new message.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testSuccessfullyCreatesNewMessage() throws Exception {
        MessageRequest messageRequest = new MessageRequest(1L, 1L, "New message");

        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(post("/api/messages")
                .header("Authorization", "Bearer " + validMockJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Successfully created a new message!"));
    }

    /**
     * EDGE CASE:
     * Test case for validating that the controller returns a bad request status
     * when required fields are missing in the request payload.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testReturnsResponseEntityWithBadRequestStatus() throws Exception {
        MessageRequest messageRequest = new MessageRequest(null, null, null); // Missing required fields

        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(post("/api/messages")
                .header("Authorization", "Bearer " + validMockJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isBadRequest());
    }

    /**
     * EDGE CASE:
     * Test case for validating that the controller throws an API exception
     * with unauthorized status when an invalid token is provided.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testThrowsApiExceptionWithUnauthorizedStatus() throws Exception {
        MessageRequest messageRequest = new MessageRequest(1L, 1L, "Test message content");

        mvc.perform(post("/api/messages")
                .header("Authorization", "Bearer INVALID_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isUnauthorized());
    }

    /**
     * EDGE CASE:
     * Test case for validating that the controller throws an API exception
     * with forbidden status when the user in the token does not have permission.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testThrowsApiExceptionWithForbiddenStatus() throws Exception {
        MessageRequest messageRequest = new MessageRequest(2L, 2L, "Test message content");

        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(post("/api/messages")
                .header("Authorization", "Bearer " + validMockJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isForbidden());
    }

    /**
     * EDGE CASE:
     * Test case for validating that the controller handles the scenario when
     * the user with the given ID does not exist.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUserWithGivenIdDoesNotExist() throws Exception {
        MessageRequest messageRequest = new MessageRequest(1L, 420L, "Test message content");

        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(post("/api/messages")
                .header("Authorization", "Bearer " + validMockJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isNotFound());
    }

    /**
     * EDGE CASE:
     * Test case for validating that the controller throws an API exception
     * when the rental is not found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testThrowsApiExceptionWhenRentalNotFound() throws Exception {
        MessageRequest messageRequest = new MessageRequest(420L, 1L, "Test message content");

        String validMockJwt = getMockJwtWithIdOfOne();
        mvc.perform(post("/api/messages")
                .header("Authorization", "Bearer " + validMockJwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
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

package com.openclassrooms.p3.dto;

/**
 * Request payload for posting a message.
 */
public record MessageRequest(Long rental_id, Long user_id, String message) {
}

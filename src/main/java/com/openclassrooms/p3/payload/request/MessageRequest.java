package com.openclassrooms.p3.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for posting a message.
 */
public record MessageRequest(@NotBlank(message = "The rental ID cannot be blank or null") Long rental_id,
        @NotBlank(message = "The user ID cannot be blank or null") Long user_id, String message) {
}

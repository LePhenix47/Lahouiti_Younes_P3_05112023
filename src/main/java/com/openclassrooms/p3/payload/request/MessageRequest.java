package com.openclassrooms.p3.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for posting a message.
 */
public record MessageRequest(
                @NotNull(message = "The rental ID cannot be null") Long rental_id,
                @NotNull(message = "The user ID cannot be null") Long user_id,
                @NotBlank(message = "The message cannot be blank or null") String message) {
}

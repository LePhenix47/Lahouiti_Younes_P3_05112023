package com.openclassrooms.p3.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request payload for user login.
 */
public record AuthLoginRequest(
                @NotBlank(message = "Email cannot be blank or null") @Pattern(regexp = "^(?![.])(?!.*[.][.])[a-zA-Z0-9.-]+@[a-zA-Z0-9]+([.][a-zA-Z]{2,8})+([.][a-zA-Z]{2,8})?$", message = "Invalid email format") String email,
                @NotBlank(message = "Password cannot be blank or null") String password) {
}
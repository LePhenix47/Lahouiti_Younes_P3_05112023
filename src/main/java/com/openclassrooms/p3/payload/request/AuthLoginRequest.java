package com.openclassrooms.p3.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request payload for user login.
 */
public record AuthLoginRequest(
        @NotBlank(message = "Email cannot be blank or null") @Pattern(regexp = "^(?![.])(?!.*[.][.])[a-zA-Z0-9.-]+@[a-zA-Z0-9]+([.][a-zA-Z]{2,8})+([.][a-zA-Z]{2,8})?$", message = "Invalid email format") String email,
        @Size(min = 6, message = "Password must be at least 6 characters long") @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-+=<>?]).*$", message = "The password must contain at least one number and one special character") String password) {
}
package com.openclassrooms.p3.payload.request;

import jakarta.validation.constraints.*;

/**
 * Request payload for user registration.
 */
public record AuthRegisterRequest(
        @NotBlank(message = "Email cannot be blank or null") @Pattern(regexp = "^(?![.])(?!.*[.][.])[a-zA-Z0-9.-]+@[a-zA-Z0-9]+([.][a-zA-Z]{2,8})+([.][a-zA-Z]{2,8})?$", message = "Invalid email format") String email,
        @NotBlank(message = "Name cannot be blank or null") @Size(min = 2, message = "Password must be at least 2 characters long") String name,
        @Size(min = 6, message = "Password must be at least 6 characters long") @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()-+=<>?]).*$", message = "The password must contain at least one number and one special character") String password) {
}

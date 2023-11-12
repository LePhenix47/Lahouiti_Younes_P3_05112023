package com.openclassrooms.p3.dto;

/**
 * Response payload for authentication operations, containing a JWT.
 */
public record AuthResponse(String token) {
}

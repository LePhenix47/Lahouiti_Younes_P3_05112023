package com.openclassrooms.p3.payload.response;

/**
 * Response payload for authentication operations, containing a JWT.
 */
public record AuthResponse(String token) {
}

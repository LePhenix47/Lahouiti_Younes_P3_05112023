package com.openclassrooms.p3.payload.request;

/**
 * Request payload for user login.
 */
public record AuthLoginRequest(String email, String password) {
}

package com.openclassrooms.p3.payload.request;

/**
 * Request payload for user registration.
 */
public record AuthRegisterRequest(String email, String name, String password) {
}

package com.openclassrooms.p3.dto;

/**
 * Request payload for user login.
 */
public record AuthLoginRequest(String email, String password) {
}

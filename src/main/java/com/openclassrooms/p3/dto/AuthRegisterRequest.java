package com.openclassrooms.p3.dto;

/**
 * Request payload for user registration.
 */
public record AuthRegisterRequest(String email, String name, String password) {
}

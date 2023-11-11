package com.openclassrooms.p3.dto;

import lombok.Value;

/**
 * Response payload for authentication operations, containing a JWT.
 */
@Value
public class AuthResponse {
    private String token;
}

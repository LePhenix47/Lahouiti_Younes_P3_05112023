package com.openclassrooms.p3.dto;

import lombok.Value;

/**
 * Request payload for user login.
 */
@Value
public class AuthLoginRequest {
    private String email;
    private String password;
}

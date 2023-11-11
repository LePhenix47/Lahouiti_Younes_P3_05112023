package com.openclassrooms.p3.dto;

import lombok.Value;

@Value
public class AuthLoginRequest {
    private String email;
    private String password;
}

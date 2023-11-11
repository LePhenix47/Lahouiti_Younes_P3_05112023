package com.openclassrooms.p3.dto;

import lombok.Value;

@Value
public class AuthRegisterRequest {
    private String email;
    private String name;
    private String password;
}

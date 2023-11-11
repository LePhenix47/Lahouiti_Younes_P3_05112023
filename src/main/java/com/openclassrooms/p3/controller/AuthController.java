package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.dto.AuthLoginRequest;
import com.openclassrooms.p3.dto.AuthRegisterRequest;
import com.openclassrooms.p3.dto.AuthResponse;
import com.openclassrooms.p3.dto.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for handling authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return A JWT if registration is successful.
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRegisterRequest request) {
        // TODO: Implement registration logic
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return A JWT if login is successful.
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthLoginRequest request) {
        // TODO: Implement login logic
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return User info.
     */
    @GetMapping("/me")
    public UserInfoResponse getMe() {
        // TODO: Implement getMe logic
    }
}

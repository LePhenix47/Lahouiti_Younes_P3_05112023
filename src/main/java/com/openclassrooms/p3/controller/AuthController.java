package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.service.UserService;

import java.util.Map;

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
    public void register(@RequestBody Map<String, String> request) {
        // TODO: Implement registration logic
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return A JWT if login is successful.
     */
    @PostMapping("/login")
    public void login(@RequestBody Map<String, String> request) {
        // TODO: Implement login logic
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return User info.
     */
    @GetMapping("/me")
    public void getMe() {
        // TODO: Implement getMe logic
    }
}

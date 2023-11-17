package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.model._User;
import com.openclassrooms.p3.payload.request.AuthLoginRequest;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for handling authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity<AuthResponse> A JWT if registration is successful.
     */
    @PostMapping("/register")
    public _User register(@RequestBody AuthRegisterRequest request) {
        _User savedUser = userService.saveUserFromRegistrationRequest(request);
        logger.info(savedUser.toString());

        return savedUser;
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return ResponseEntity<AuthResponse> A JWT if login is successful.
     */
    @PostMapping("/login")
    public void login(@RequestBody AuthLoginRequest request) {
        // TODO: Implement login logic
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return ResponseEntity<AuthResponse> containing the user info.
     */
    @GetMapping("/me")
    public void getMe() {
        // TODO: Implement getMe logic
    }
}

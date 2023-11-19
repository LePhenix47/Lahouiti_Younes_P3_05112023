package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.exception.ApiErrorResponse;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.AuthLoginRequest;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.payload.response.AuthResponse;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * Controller for handling authentication-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity<AuthResponse> A JWT if registration is successful.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRegisterRequest request,
            BindingResult bindingResult) {

        try {
            Boolean payloadIsInvalid = bindingResult.hasErrors();
            if (payloadIsInvalid) {
                // Refactored to use handlePayloadError method
                GlobalExceptionHandler.handlePayloadError("Bad payload", bindingResult, HttpStatus.BAD_REQUEST);
            }

            Boolean hasAlreadyRegistered = userService.isEmailInUse(request.email());
            if (hasAlreadyRegistered) {
                // Refactored to use handleLogicError method
                GlobalExceptionHandler.handleLogicError("Email is already in use", HttpStatus.CONFLICT);
            }

            Users user = userService.saveUserBySignUp(request);

            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            String jwtToken = JwtUtil.generateJwtToken(userEntity.id());

            AuthResponse authResponse = new AuthResponse(jwtToken);

            // Return the saved user with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return ResponseEntity<AuthResponse> A JWT if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) {
        // TODO: Implement login logic
        try {
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return ResponseEntity<AuthResponse> containing the user info.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        // TODO: Implement getMe logic
        try {
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }
}

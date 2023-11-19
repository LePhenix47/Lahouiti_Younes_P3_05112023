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
import java.util.Optional;

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
                GlobalExceptionHandler.handlePayloadError("Bad payload", bindingResult, HttpStatus.BAD_REQUEST);
            }

            Boolean hasAlreadyRegistered = userService.isEmailInUse(request.email());
            if (hasAlreadyRegistered) {
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
    public ResponseEntity<?> login(@Valid @RequestBody AuthLoginRequest request, BindingResult bindingResult) {
        try {
            Boolean payloadIsInvalid = bindingResult.hasErrors();
            if (payloadIsInvalid) {
                GlobalExceptionHandler.handlePayloadError("Bad payload", bindingResult, HttpStatus.BAD_REQUEST);
            }

            // TODO: we need to check if the email exists → .orElseThrow
            Optional<Users> optionalUser = userService.getUserByEmail(request.email());

            Users user = optionalUser.get();

            Boolean passwordIsIncorrect = !userService.isPasswordValid(request.password(), user);
            if (passwordIsIncorrect) {
                GlobalExceptionHandler.handleLogicError("Invalid password", HttpStatus.UNAUTHORIZED);
            }

            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            String jwtToken = JwtUtil.generateJwtToken(userEntity.id());

            AuthResponse authResponse = new AuthResponse(jwtToken);
            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
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
    public ResponseEntity<?> getMe(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract JWT from Authorization header
            String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);
            return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
            // // Extract user ID from JWT
            // Long userId = JwtUtil.extractUserId(jwtToken);

            // // Fetch user information based on the user ID
            // Optional<Users> optionalUser = userService.getUserById(userId);

            // Users user = optionalUser.get();
            // // Convert user information to DTO
            // UserInfoResponse userEntity = userMapper.toDtoUser(user);

            // // Check if the user exists
            // if (optionalUser.isEmpty()) {
            // // Handle scenario where user is not found
            // GlobalExceptionHandler.handleLogicError("User not found",
            // HttpStatus.NOT_FOUND);
            // }
            // return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }
}

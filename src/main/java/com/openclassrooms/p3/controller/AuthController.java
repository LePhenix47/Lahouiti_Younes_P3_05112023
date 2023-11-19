package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.AuthLoginRequest;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.payload.response.AuthResponse;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

/**
 * Controller for handling authentication-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
                // Handle validation errors
                List<String> payloadErrors = new ArrayList<>();
                bindingResult.getAllErrors().forEach(error -> payloadErrors.add(error.getDefaultMessage()));

                // Throw ApiException with validation errors
                throw new ApiException("Bad payload errors", payloadErrors, null, HttpStatus.BAD_REQUEST,
                        LocalDateTime.now());
            }

            Boolean hasAlreadyRegistered = userService.isEmailInUse(request.email());
            if (hasAlreadyRegistered) {
                List<String> errorMessageList = new ArrayList<>();
                errorMessageList.add("Email is already in use");

                // Throw ApiException with a specific error message
                throw new ApiException("Email is already in use", errorMessageList, null, HttpStatus.CONFLICT,
                        LocalDateTime.now());
            }

            Users user = userService.saveUserBySignUp(request);

            // Map the request to Users entity using the mapper
            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            // Generate JWT token
            String jwtToken = JwtUtil.generateJwtToken(userEntity.id());

            // Return the saved user along with the JWT token
            AuthResponse authResponse = new AuthResponse(jwtToken);

            // Return the saved user with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (ApiException ex) {
            Map<String, Object> errorResponse = ex.toErrorResponseMap();
            return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
        }

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

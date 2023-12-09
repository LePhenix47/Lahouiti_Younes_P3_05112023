package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.AuthLoginRequest;
import com.openclassrooms.p3.payload.request.AuthRegisterRequest;
import com.openclassrooms.p3.payload.response.AuthResponse;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;
import com.openclassrooms.p3.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * Controller for handling authentication-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
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
    @Operation(description = "Registers a new user", summary = "Registers a new user", responses = {
            @ApiResponse(description = "Successfully registered", responseCode = "201", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthResponse.class), examples = @ExampleObject(value = "{\"token\": \"[GENERATED_JWT]\"}"))
            }),
            @ApiResponse(description = "Bad payload", responseCode = "400"),
            @ApiResponse(description = "User already registered", responseCode = "409"),
    })
    public ResponseEntity<?> register(@Valid @RequestBody AuthRegisterRequest request,
            BindingResult bindingResult) {

        try {
            checkBodyPayloadErrors(bindingResult);

            checkIfEmailIsInUse(request.email());

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
    @Operation(description = "Logs in an existing user", summary = "Logs in an existing user", responses = {
            @ApiResponse(description = "Successfully retrieved user info", responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthResponse.class), examples = @ExampleObject(value = "{\"token\": \"[GENERATED_JWT]\"}"))
            }),
            @ApiResponse(description = "Bad payload", responseCode = "400"),
            @ApiResponse(description = "User with inputted email not found", responseCode = "404"),
    })
    public ResponseEntity<?> login(@Valid @RequestBody AuthLoginRequest request, BindingResult bindingResult) {
        try {
            checkBodyPayloadErrors(bindingResult);

            Users user = getUserByEmail(request.email());
            checkUserPassword(request.password(), user);

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
    @Operation(description = "Retrieves information about the currently authenticated user", summary = "Retrieves information about the currently authenticated user", responses = {
            @ApiResponse(description = "Successfully retrieved user info", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"created_at\":\"2023-12-07T12:00:00.000Z\",\"updated_at\":\"2023-12-07T12:30:00.000Z\"}")) }),
            @ApiResponse(description = "Bad payload", responseCode = "400"),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
            @ApiResponse(description = "User not found", responseCode = "404"),
    }, security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<?> getMe(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);
            UserInfoResponse userEntity = verifyAndGetUserByTokenId(userIdFromToken);

            return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Checks if there are any payload errors in the request body.
     *
     * @param bindingResult The BindingResult object that holds the validation
     *                      errors.
     */
    private void checkBodyPayloadErrors(BindingResult bindingResult) {
        Boolean payloadIsInvalid = bindingResult.hasErrors();
        if (payloadIsInvalid) {
            GlobalExceptionHandler.handlePayloadError("Bad request", bindingResult, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the user ID from the authorization header.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return The user ID extracted from the JWT token.
     */
    private Long getUserIdFromAuthorizationHeader(String authorizationHeader) {
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

        Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        if (hasJwtExtractionError) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return optionalUserIdFromToken.get();
    }

    /**
     * Retrieves the user information as a DTO entity based on the user ID extracted
     * from the JWT
     * token.
     * 
     * @param userIdFromToken The user ID extracted from the JWT token.
     * @return The user information as a UserInfoResponse object.
     * @throws ApiException If the user with the given ID does not exist or if there
     *                      is a mismatch between the user ID and the token.
     */
    private UserInfoResponse verifyAndGetUserByTokenId(Long userIdFromToken) {
        // Fetch user information based on the user ID
        Optional<Users> optionalSpecificUser = userService.getUserById(userIdFromToken);
        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        Users user = optionalSpecificUser.get();
        // Convert user information to DTO
        UserInfoResponse userEntity = userMapper.toDtoUser(user);

        return userEntity;
    }

    /**
     * Checks if an email is already registered in the system.
     *
     * @param email The email to check if it is already registered.
     */
    private void checkIfEmailIsInUse(String email) {
        Boolean hasAlreadyRegistered = userService.isEmailInUse(email);
        if (hasAlreadyRegistered) {
            GlobalExceptionHandler.handleLogicError("Conflict", HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The user with the specified email.
     * @throws ApiException If the user with the given email does not exist.
     */
    private Users getUserByEmail(String email) {
        Optional<Users> optionalUser = userService.getUserByEmail(email);

        Boolean userNotFound = optionalUser.isEmpty();
        if (userNotFound) {
            GlobalExceptionHandler.handleLogicError("Not found", HttpStatus.NOT_FOUND);
        }

        return optionalUser.get();
    }

    /**
     * Checks if the provided password is correct for the given user.
     *
     * @param requestPassword The password to check.
     * @param user            The user to check the password against.
     */
    private void checkUserPassword(String requestPassword, Users user) {
        Boolean passwordIsIncorrect = !userService.isPasswordValid(requestPassword, user);
        if (passwordIsIncorrect) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}

package com.openclassrooms.p3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;
import com.openclassrooms.p3.utils.JwtUtil;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for handling user-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
@Tag(name = "Users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * Retrieves information about a specific user.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity<UserInfoResponse> with user information.
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieves a user by their ID", summary = "Retrieves a user by their ID", responses = {
            @ApiResponse(description = "Successfully retrieved the user by their ID", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"created_at\":\"2023-12-07T12:00:00.000Z\",\"updated_at\":\"2023-12-07T12:30:00.000Z\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
            @ApiResponse(description = "User not found", responseCode = "404"),
    })
    public ResponseEntity<?> getUser(@PathVariable final Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            getUserIdFromAuthorizationHeader(authorizationHeader);

            UserInfoResponse userEntity = verifyAndGetUserByTokenId(id);

            return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
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
            GlobalExceptionHandler.handleLogicError("Not authorized", HttpStatus.UNAUTHORIZED);
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
}

package com.openclassrooms.p3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;

/**
 * Controller for handling user-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
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
    public ResponseEntity<?> getUser(@RequestParam final Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract JWT from Authorization header
            String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

            // Extract user ID from JWT
            Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

            Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
            if (hasJwtExtractionError) {
                GlobalExceptionHandler.handleLogicError("An unexpected client error occurred", HttpStatus.UNAUTHORIZED);
            }

            // Fetch user information based on the user ID
            Optional<Users> optionalSpecificUser = userService.getUserById(id);
            Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
            if (userWithIdDoesNotExist) {
                GlobalExceptionHandler.handleLogicError("User does not exist", HttpStatus.NOT_FOUND);
            }

            Users user = optionalSpecificUser.get();
            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }
}

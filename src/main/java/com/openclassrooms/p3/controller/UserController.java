package com.openclassrooms.p3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.JwtService;
import com.openclassrooms.p3.service.UserService;

/**
 * Controller for handling user-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtService jwtService;

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
            // ! check the JWT

            // Fetch user information based on the user ID
            Optional<Users> optionalUser = userService.getUserById(id);

            // Check if the user exists
            if (optionalUser.isEmpty()) {
            }

            Users user = optionalUser.get();

            // Convert user information to DTO
            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            // Return the user with a 200 OK status
            return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        } catch (ApiException ex) {
            // Handle any other exceptions that may occur
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }
}

package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.dto.UserInfoResponse;
import com.openclassrooms.p3.service.UserService;

/**
 * Controller for handling user-related operations.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves information about a specific user.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with user information.
     */
    @GetMapping("/")
    public UserInfoResponse getUser(@RequestParam final Long id) {
        // TODO: Implement getUser logic
    }
}

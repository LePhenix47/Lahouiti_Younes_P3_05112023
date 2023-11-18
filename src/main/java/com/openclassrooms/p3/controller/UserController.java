package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Retrieves information about a specific user.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity<UserInfoResponse> with user information.
     */
    @GetMapping("/")
    public void getUser(@RequestParam final Long id) {
        // TODO: Implement getUser logic
    }
}

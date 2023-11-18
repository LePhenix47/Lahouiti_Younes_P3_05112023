package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/")
    public void getUser(@RequestParam final Long id) {
        // TODO: Implement getUser logic
        // Users user = userService.getUserFromId(id);

        // UserInfoResponse userEntity = userMapper.toDtoUser(user);
        // Return the saved user with a 201 Created status
        // return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }
}

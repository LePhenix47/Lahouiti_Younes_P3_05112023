package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.dto.AuthLoginRequest;
import com.openclassrooms.p3.dto.AuthRegisterRequest;
import com.openclassrooms.p3.dto.AuthResponse;
import com.openclassrooms.p3.dto.UserInfoResponse;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.model._User;
import com.openclassrooms.p3.service.UserService;
import com.openclassrooms.p3.utils.JwtUtil;

import java.security.Principal;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

/**
 * Controller for handling authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return AuthResponse A JWT if registration is successful.
     */
    @PostMapping("/register")
    public void register(@RequestBody AuthRegisterRequest request) {
        try {
            _User newUser = new _User();
            newUser.setEmail(request.email());
            newUser.setName(request.name());
            newUser.setPassword(request.password());

            _User savedUser = userService.saveUser(newUser);

            // For simplicity, let's assume a successful registration generates a token
            String token = JwtUtil.generateJwtToken(savedUser.getId());
            // return new AuthResponse(token);
        } catch (Exception e) {
            throw new ApiException("Error during user registration", e, HttpStatus.INTERNAL_SERVER_ERROR,
                    ZonedDateTime.now());
        }
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return AuthResponse A JWT if login is successful.
     */
    @PostMapping("/login")
    public void login(@RequestBody AuthLoginRequest request) {
        // TODO: Implement login logic
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return AuthResponse containing the user info.
     */
    @GetMapping("/me")
    public void getMe() {
        // TODO: Implement getMe logic
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if (token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append("Welcome, " + u.getUsername());
        } else {
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }
}

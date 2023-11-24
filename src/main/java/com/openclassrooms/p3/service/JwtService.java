package com.openclassrooms.p3.service;

import org.springframework.beans.factory.annotation.Autowired;
// JwtUserService.java
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.service.UserService;

import java.util.Optional;

@Service
public class JwtService {

    @Autowired
    private UserService userService;

    /**
     * Retrieves information about the user based on the JWT.
     *
     * @param authorizationHeader The Authorization header containing the JWT.
     * @return The user information.
     * @throws ApiException if there is an error during the process.
     */
    public Users getUserFromJwt(String authorizationHeader) throws ApiException {
        // Extract JWT from Authorization header
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Long userId = JwtUtil.extractUserId(jwtToken);

        // Fetch user information based on the user ID
        Optional<Users> optionalUser = userService.getUserById(userId);
        return optionalUser.get();
    }
}

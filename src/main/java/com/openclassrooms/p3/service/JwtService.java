package com.openclassrooms.p3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.model.Users;
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
     */
    public Optional<Users> getUserFromJwt(String authorizationHeader) {
        // Extract JWT from Authorization header
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> userId = JwtUtil.extractUserId(jwtToken);

        // Fetch user information based on the user ID
        Optional<Users> optionalUser = userService.getUserById(userId.get());

        return optionalUser;
    }
}

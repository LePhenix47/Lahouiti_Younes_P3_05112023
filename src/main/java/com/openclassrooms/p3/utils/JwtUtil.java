package com.openclassrooms.p3.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Generates a JWT for the user with the specified ID.
     *
     * @param id User ID.
     * @return The JWT generated with the "Bearer " prefix.
     */
    public static String generateJwtToken(Long id) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(id))
                .signWith(SECRET_KEY)
                .compact();

        return "Bearer " + token;
    }
}
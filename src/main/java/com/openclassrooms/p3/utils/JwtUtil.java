package com.openclassrooms.p3.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Optional;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Generates a JWT for the user with the specified ID.
     *
     * @param id User ID.
     * @return The JWT generated.
     */
    public static String generateJwtToken(Long id) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(id))
                .signWith(SECRET_KEY)
                .compact();

        return token;
    }

    /**
     * Checks if a JWT token is valid.
     *
     * @param token The JWT token.
     * @return True if the token is valid; false otherwise.
     */
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            // Token validation failed
            return false;
        }
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token The JWT token.
     * @return The user ID extracted from the token, or empty Optional in case of an
     *         exception.
     */
    public static Optional<Long> extractUserId(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            return Optional.of(Long.parseLong(claims.getSubject()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Extracts the JWT from the Authorization header.
     *
     * @param authorizationHeader The Authorization header containing the JWT.
     * @return The extracted JWT.
     */
    public static String extractJwtFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(7); // Skip "Bearer " to get the actual JWT
    }
}

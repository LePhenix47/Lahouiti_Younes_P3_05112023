package com.openclassrooms.p3.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.openclassrooms.p3.exception.ApiException;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Generates a JWT for the user with the specified ID.
     *
     * @param id User ID.
     * @return The JWT generated.
     * @throws ApiException if there is an error during JWT generation.
     */
    public static String generateJwtToken(Long id) {
        try {
            String token = Jwts.builder()
                    .setSubject(String.valueOf(id))
                    .signWith(SECRET_KEY)
                    .compact();

            return token;
        } catch (Exception e) {
            // Handle the exception and convert it to ApiException
            List<String> errors = new ArrayList<>();
            errors.add("Error generating JWT token");
            throw new ApiException("JWT generation failed", errors, HttpStatus.INTERNAL_SERVER_ERROR,
                    LocalDateTime.now());
        }
    }

    /**
     * Validates a JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid
     * @throws ApiException if otherwise the token is invalid
     */
    public static boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Token validation failed, convert it to ApiException
            List<String> errors = new ArrayList<>();
            errors.add("Error validating JWT token");
            throw new ApiException("JWT validation failed", errors, HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        }
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token The JWT token.
     * @return The user ID extracted from the token.
     * @throws ApiException if there is an error during token extraction.
     */
    public static Long extractUserId(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            // * Token extraction failed
            List<String> errors = new ArrayList<>();
            errors.add("Error extracting user ID from JWT token");
            throw new ApiException("JWT token extraction failed", errors, HttpStatus.INTERNAL_SERVER_ERROR,
                    LocalDateTime.now());
        }
    }
}

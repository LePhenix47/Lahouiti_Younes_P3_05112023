package com.openclassrooms.p3.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import org.springframework.http.HttpStatus;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Generates a JWT for the user with the specified ID.
     *
     * @param id User ID.
     * @return The JWT generated.
     * @throws ApiException if there is an error during JWT generation.
     */
    public static String generateJwtToken(Long id) throws ApiException {
        try {
            String token = Jwts.builder()
                    .setSubject(String.valueOf(id))
                    .signWith(SECRET_KEY)
                    .compact();

            return token;
        } catch (Exception exception) {
            // Handle the exception and convert it to ApiException
            GlobalExceptionHandler.handleLogicError("JWT generation failed: " + exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
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
     * @return The user ID extracted from the token.
     * @throws ApiException if there is an error during token extraction.
     */
    public static Long extractUserId(String token) throws ApiException {
        try {
            Boolean jwtIsInvalid = !isTokenValid(token);
            if (jwtIsInvalid) {
                GlobalExceptionHandler.handleLogicError("The provided JWT is invalid", HttpStatus.BAD_REQUEST);
            }

            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception exception) {
            // Token extraction failed, convert it to ApiException
            GlobalExceptionHandler.handleLogicError(
                    "JWT token extraction failed: " + exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    /**
     * Extracts the JWT from the Authorization header.
     *
     * @param authorizationHeader The Authorization header containing the JWT.
     * @return The extracted JWT.
     * @throws ApiException if there is an error during JWT extraction.
     */
    public static String extractJwtFromHeader(String authorizationHeader) throws ApiException {
        try {
            // Assuming the header format is "Bearer <JWT>"
            return authorizationHeader.substring(7); // Skip "Bearer " to get the actual JWT
        } catch (Exception exception) {
            GlobalExceptionHandler.handleLogicError("JWT extraction from headers failed: ", HttpStatus.BAD_REQUEST);
            return null;
        }
    }
}

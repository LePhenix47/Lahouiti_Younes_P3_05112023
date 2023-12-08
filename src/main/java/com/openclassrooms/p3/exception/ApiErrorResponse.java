package com.openclassrooms.p3.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an error response for an API.
 * 
 * <p>
 * The {@code ApiErrorResponse} class is a record class in Java that represents
 * an error response for an API.
 * It contains information such as the error message, a list of errors, the HTTP
 * status code, and the date and time when the error occurred.
 * 
 * <p>
 * Example Usage:
 * 
 * <pre>{@code
 * ApiErrorResponse errorResponse = new ApiErrorResponse("Internal Server Error", List.of("Database connection failed"),
 *         HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
 * }</pre>
 * 
 * <p>
 * Fields:
 * <ul>
 * <li>{@code message}: Represents the main error message.</li>
 * <li>{@code errors}: Represents a list of specific errors.</li>
 * <li>{@code httpStatus}: Represents the HTTP status code of the error
 * response.</li>
 * <li>{@code date}: Represents the date and time when the error occurred.</li>
 * </ul>
 * 
 * <p>
 * Methods:
 * <ul>
 * <li>No additional methods beyond the default methods provided by the record
 * class.</li>
 * </ul>
 */
public record ApiErrorResponse(String message, List<String> errors, HttpStatus httpStatus,
        LocalDateTime date) {
}

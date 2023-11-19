package com.openclassrooms.p3.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Global exception handler for handling instances of ApiException in the
 * application.
 * This class is annotated with {@link ControllerAdvice}, making it applicable
 * to all
 * controllers in the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles instances of {@link ApiException} and constructs a proper response
     * entity with details of the exception.
     *
     * @param exception The ApiException to handle.
     * @return A ResponseEntity with an appropriate status code and error details.
     */
    @ExceptionHandler(value = ApiException.class)
    public static ResponseEntity<Object> handleApiException(ApiException exception) {
        logger.error("API Exception: {}", exception.getMessage(), exception);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                exception.getMessage(),
                exception.getErrors(),
                exception.getHttpStatus(),
                exception.getDate());

        return ResponseEntity.status(exception.getHttpStatus()).body(apiErrorResponse);
    }

    /**
     * Handles payload errors and constructs a proper response entity
     * with details of the exception.
     *
     * @param errorMessage  The error message to be included in the exception.
     * @param bindingResult The BindingResult containing validation errors.
     * @param httpStatus    The HTTP status code to be set in the response.
     * @throws ApiException Throws an ApiException with the provided error details.
     */
    public static void handlePayloadError(String errorMessage, BindingResult bindingResult,
            HttpStatus httpStatus) {
        logger.error("Payload Error: {}", errorMessage);

        List<String> payloadErrors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> payloadErrors.add(error.getDefaultMessage()));

        throw new ApiException(errorMessage, payloadErrors, httpStatus,
                LocalDateTime.now());
    }

    /**
     * Handles logic errors and constructs a proper response entity
     * with details of the exception.
     *
     * @param errorMessage The error message to be included in the exception.
     * @param httpStatus   The HTTP status code to be set in the response.
     * @throws ApiException Throws an ApiException with the provided error details.
     */
    public static void handleLogicError(String errorMessage, HttpStatus httpStatus) {
        logger.error("Logic Error: {}", errorMessage);

        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add(errorMessage);

        throw new ApiException(errorMessage, errorMessageList, httpStatus,
                LocalDateTime.now());
    }
}

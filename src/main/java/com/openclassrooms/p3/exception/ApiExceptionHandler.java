package com.openclassrooms.p3.exception;

import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException exception) {
        logger.error("API Exception: {}", exception.getMessage(), exception);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                exception.getMessage(),
                exception.getHttpStatus(),
                ZonedDateTime.now());
        return new ResponseEntity<>(apiErrorResponse, exception.getHttpStatus());
    }
}

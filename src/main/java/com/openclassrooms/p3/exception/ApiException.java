package com.openclassrooms.p3.exception;

import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Custom exception class for handling API-related errors.
 *
 * This exception includes details such as the error message, a list of
 * validation errors,
 * the underlying throwable (if any), the HTTP status code, and the timestamp of
 * the occurrence.
 */
public class ApiException extends RuntimeException {
        /**
         * The main error message describing the exception.
         */
        private final String message;

        /**
         * A list of validation errors in case of payload validation failures.
         */
        private final List<String> errors;

        /**
         * The HTTP status code associated with this exception.
         */
        private final HttpStatus httpStatus;

        /**
         * The timestamp when this exception occurred.
         */
        private final LocalDateTime date;

        public ApiException(String message, List<String> errors, HttpStatus httpStatus, LocalDateTime date) {
                super(message);

                this.message = message;
                this.errors = errors;
                this.httpStatus = httpStatus;
                this.date = date;
        }

        /**
         * Gets the main error message.
         *
         * @return The error message.
         */
        public String getMessage() {
                return message;
        }

        /**
         * Gets the list of validation errors.
         *
         * @return A list of validation errors.
         */
        public List<String> getErrors() {
                return errors;
        }

        /**
         * Gets the HTTP status code associated with this exception.
         *
         * @return The HTTP status code.
         */
        public HttpStatus getHttpStatus() {
                return httpStatus;
        }

        /**
         * Gets the timestamp when this exception occurred.
         *
         * @return The timestamp.
         */
        public LocalDateTime getDate() {
                return date;
        }
}

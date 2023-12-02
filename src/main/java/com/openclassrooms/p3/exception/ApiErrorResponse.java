package com.openclassrooms.p3.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(String message, List<String> errors, HttpStatus httpStatus,
                LocalDateTime date) {
}

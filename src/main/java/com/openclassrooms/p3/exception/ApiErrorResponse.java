package com.openclassrooms.p3.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiErrorResponse(String message, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
}

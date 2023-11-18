package com.openclassrooms.p3.payload.request;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload containing the Form Data for updating a rental.
 */
public record RentalUpdateRequest(
        @NotBlank(message = "Name cannot be blank or null") String name,
        Integer surface,
        Double price,
        @Nullable MultipartFile picture,
        String description) {
}

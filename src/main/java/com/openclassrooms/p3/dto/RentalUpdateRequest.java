package com.openclassrooms.p3.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Request payload containing the Form Data for updating a rental.
 */
public record RentalUpdateRequest(
        String name,
        Integer surface,
        Double price,
        MultipartFile picture,
        String description) {
}

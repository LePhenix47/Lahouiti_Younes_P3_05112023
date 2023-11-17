package com.openclassrooms.p3.payload.request;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request payload containing the Form Data for updating a rental.
 */
public record RentalUpdateRequest(
                String name,
                Integer surface,
                Double price,
                @Nullable MultipartFile picture,
                String description) {
}

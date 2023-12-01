package com.openclassrooms.p3.payload.request;

import java.math.BigDecimal;

import org.springframework.lang.Nullable;

/**
 * Request payload containing the Form Data for updating a rental.
 */
public record RentalUpdateRequest(
        String name,
        Integer surface,
        BigDecimal price,
        String description,
        @Nullable String picture,
        Long owner_id) {
}

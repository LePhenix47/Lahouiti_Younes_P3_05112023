package com.openclassrooms.p3.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Response payload for retrieving a single rental.
 */
public record RentalSingleResponse(
        Long id,
        String name,
        Integer surface,
        BigDecimal price,
        String picture,
        String description,
        Long owner_id,
        Date created_at,
        Date updated_at) {
}

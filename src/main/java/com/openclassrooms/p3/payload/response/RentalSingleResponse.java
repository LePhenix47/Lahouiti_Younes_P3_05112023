package com.openclassrooms.p3.payload.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
                LocalDateTime created_at,
                LocalDateTime updated_at) {
}

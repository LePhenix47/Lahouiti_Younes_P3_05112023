package com.openclassrooms.p3.dto;

import java.util.List;

/**
 * Response payload for retrieving all rentals.
 */
public record RentalAllResponse(List<RentalSingleResponse> rentals) {
}

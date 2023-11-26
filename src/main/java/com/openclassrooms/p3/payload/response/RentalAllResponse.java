package com.openclassrooms.p3.payload.response;

/**
 * Response payload for retrieving all rentals.
 */
public record RentalAllResponse(Iterable<RentalSingleResponse> rentals) {
}

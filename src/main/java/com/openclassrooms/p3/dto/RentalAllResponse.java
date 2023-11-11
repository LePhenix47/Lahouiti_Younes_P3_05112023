package com.openclassrooms.p3.dto;

import lombok.Value;

import java.util.List;

/**
 * Response payload for retrieving all rentals.
 */
@Value
public class RentalAllResponse {
    private List<RentalSingleResponse> rentals;
}

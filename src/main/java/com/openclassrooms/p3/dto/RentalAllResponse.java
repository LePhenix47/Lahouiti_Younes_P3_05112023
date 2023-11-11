package com.openclassrooms.p3.dto;

import lombok.Value;

import java.util.List;

@Value
public class RentalAllResponse {
    private List<RentalSingleResponse> rentals;
}

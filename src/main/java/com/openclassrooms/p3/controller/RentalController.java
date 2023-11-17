package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.payload.request.RentalUpdateRequest;
import com.openclassrooms.p3.payload.response.RentalAllResponse;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;
import com.openclassrooms.p3.payload.response.ResponseMessage;
import com.openclassrooms.p3.service.RentalService;

/**
 * Controller for handling rental-related operations.
 */
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    /**
     * Retrieves all rentals.
     *
     * @return ResponseEntity<RentalAllResponse> with an array of rentals.
     */
    @GetMapping("/")
    public void getRentals() {
        // TODO: Implement getRentals logic
    }

    /**
     * Retrieves information about a specific rental.
     *
     * @param id The ID of the rental to retrieve.
     * @return ResponseEntity<RentalSingleResponse> with rental information.
     */
    @GetMapping("/{id}")
    public void getRental(@PathVariable final Long id) {
        // TODO: Implement getRental logic
    }

    /**
     * Adds a new rental.
     *
     * @param request The rental request containing details.
     * @return ResponseEntity<ResponseMessage> with information about the rental
     *         addition.
     */
    @PostMapping("/")
    public void addRental(@RequestParam RentalUpdateRequest request) {
        // TODO: Implement addRental logic
    }

    /**
     * Updates information about a specific rental.
     *
     * @param id      The ID of the rental to update.
     * @param request The rental request containing details for the update.
     * @return ResponseEntity<ResponseMessage> with information about the rental
     *         update.
     */
    @PutMapping("/{id}")
    public void updateRental(@PathVariable final Long id,
            @RequestParam RentalUpdateRequest request) {
        // TODO: Implement updateRental logic
    }
}

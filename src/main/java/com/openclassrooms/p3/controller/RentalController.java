package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.service.RentalService;

import java.util.Map;

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
     * @return ResponseEntity with an array of rentals.
     */
    @GetMapping("/")
    public void getRentals() {
        // TODO: Implement getRentals logic
    }

    /**
     * Retrieves information about a specific rental.
     *
     * @param id The ID of the rental to retrieve.
     * @return ResponseEntity with rental information.
     */
    @GetMapping("/{id}")
    public void getRental(@PathVariable Long id) {
        // TODO: Implement getRental logic
    }

    /**
     * Adds a new rental.
     *
     * @param request The rental request containing details.
     * @return ResponseEntity with information about the rental addition.
     */
    @PostMapping("/")
    public void addRental(@RequestParam Map<String, Object> request) {
        // TODO: Implement addRental logic
    }

    /**
     * Updates information about a specific rental.
     *
     * @param id      The ID of the rental to update.
     * @param request The rental request containing details for the update.
     * @return ResponseEntity with information about the rental update.
     */
    @PutMapping("/{id}")
    public void updateRental(@PathVariable Long id, @RequestParam Map<String, Object> request) {
        // TODO: Implement updateRental logic
    }
}

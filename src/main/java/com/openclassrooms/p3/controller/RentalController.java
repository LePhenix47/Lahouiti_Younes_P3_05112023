package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @GetMapping("/")
    public ResponseEntity<?> getRentals() {
        // TODO: Implement getRentals logic
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRental(@PathVariable Long id) {
        // TODO: Implement getRental logic
    }

    @PostMapping("/")
    public ResponseEntity<?> addRental(@RequestParam Map<String, Object> request) {
        // TODO: Implement addRental logic
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestParam Map<String, Object> request) {
        // TODO: Implement updateRental logic
    }
}

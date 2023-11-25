package com.openclassrooms.p3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.RentalMapper;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.RentalUpdateRequest;
import com.openclassrooms.p3.payload.response.RentalAllResponse;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;
// import com.openclassrooms.p3.payload.response.RentalAllResponse;
// import com.openclassrooms.p3.payload.response.RentalSingleResponse;
// import com.openclassrooms.p3.payload.response.ResponseMessage;
import com.openclassrooms.p3.service.RentalService;
import com.openclassrooms.p3.service.UserService;

/**
 * Controller for handling rental-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all rentals.
     *
     * @return ResponseEntity<RentalAllResponse> with an array of rentals.
     */
    @GetMapping("")
    public ResponseEntity<?> getRentals(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

            // Extract user ID from JWT
            Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

            Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
            if (hasJwtExtractionError) {
                GlobalExceptionHandler.handleLogicError("An unexpected client error occurred", HttpStatus.UNAUTHORIZED);
            }
            Long userIdFromToken = optionalUserIdFromToken.get();
            // Fetch user information based on the user ID
            Optional<Users> optionalSpecificUser = userService.getUserById(userIdFromToken);
            Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
            if (userWithIdDoesNotExist) {
                GlobalExceptionHandler.handleLogicError("User does not exist",
                        HttpStatus.NOT_FOUND);
            }

            Iterable<Rental> allRentals = rentalService.getRentals();
            List<RentalSingleResponse> rentalDtos = rentalMapper.toDtoRentals(allRentals);

            return ResponseEntity.status(HttpStatus.OK).body(rentalDtos);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
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

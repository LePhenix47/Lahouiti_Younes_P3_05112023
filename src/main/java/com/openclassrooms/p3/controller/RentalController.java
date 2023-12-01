package com.openclassrooms.p3.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.p3.configuration.JwtUtil;
import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.RentalMapper;
import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.response.RentalAllResponse;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;
// import com.openclassrooms.p3.payload.response.RentalAllResponse;
// import com.openclassrooms.p3.payload.response.RentalSingleResponse;
// import com.openclassrooms.p3.payload.response.ResponseMessage;
import com.openclassrooms.p3.service.RentalService;
import com.openclassrooms.p3.service.S3Service;
import com.openclassrooms.p3.service.UserService;

import jakarta.validation.Valid;

/**
 * Controller for handling rental-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private S3Service s3Service;

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
            checkTokenUserId(userIdFromToken);

            Iterable<Rental> allRentals = rentalService.getRentals();
            Iterable<RentalSingleResponse> rentalDtos = rentalMapper.toDtoRentals(allRentals);

            RentalAllResponse rentalAllResponse = new RentalAllResponse(rentalDtos);

            return ResponseEntity.status(HttpStatus.OK).body(rentalAllResponse);
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
    public ResponseEntity<?> getRental(@PathVariable final Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
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

            Optional<Rental> optionalRental = rentalService.getRental(id);
            Boolean rentalDoesNotExist = optionalRental.isEmpty();
            if (rentalDoesNotExist) {
                GlobalExceptionHandler.handleLogicError("Rental ID does not exist",
                        HttpStatus.NOT_FOUND);
            }

            Rental rental = optionalRental.get();

            RentalSingleResponse rentalDto = rentalMapper.toDtoRental(rental);

            return ResponseEntity.status(HttpStatus.OK).body(rentalDto);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Adds a new rental.
     *
     * @param request The rental request containing details.
     * @return ResponseEntity<ResponseMessage> with information about the rental
     *         addition.
     */
    @PostMapping("")
    public ResponseEntity<?> addRental(@RequestParam("name") String name,
            @Valid @RequestParam("surface") Integer surface,
            @Valid @RequestParam("price") BigDecimal price,
            @Valid @RequestParam("description") String description,
            @Valid @RequestParam(value = "picture", required = false) MultipartFile picture,
            @Valid @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // TODO: Validate the payload

            // String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

            // // Extract user ID from JWT
            // Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

            // Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
            // if (hasJwtExtractionError) {
            // GlobalExceptionHandler.handleLogicError("An unexpected client error
            // occurred", HttpStatus.UNAUTHORIZED);
            // }
            // Long userIdFromToken = optionalUserIdFromToken.get();
            // // Fetch user information based on the user ID
            // Optional<Users> optionalSpecificUser =
            // userService.getUserById(userIdFromToken);
            // Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
            // if (userWithIdDoesNotExist) {
            // GlobalExceptionHandler.handleLogicError("User does not exist",
            // HttpStatus.NOT_FOUND);
            // }

            // Upload image to S3
            // TODO: Upload the image to the S3 bucket
            // TODO: Save the entire rental with only the AWS S3 URL of the picture
            // TODO: Return a string message indicating if the rental upload was successful.

            String imageUrl = s3Service.uploadFile(picture, "images");

            return ResponseEntity.status(HttpStatus.CREATED).body("Image URL: " + imageUrl);

        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
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
    public ResponseEntity<?> updateRental(@PathVariable final Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Integer surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.status(HttpStatus.OK).body("Test PUT response for the route api/rentals/{id}");
        // try {
        // String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);
        // // Extract user ID from JWT
        // Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);
        // Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        // if (hasJwtExtractionError) {
        // GlobalExceptionHandler.handleLogicError("An unexpected client error
        // occurred", HttpStatus.UNAUTHORIZED);
        // }
        // Long userIdFromToken = optionalUserIdFromToken.get();
        // // Fetch user information based on the user ID
        // Optional<Users> optionalSpecificUser =
        // userService.getUserById(userIdFromToken);
        // Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        // if (userWithIdDoesNotExist) {
        // GlobalExceptionHandler.handleLogicError("User does not exist",
        // HttpStatus.NOT_FOUND);
        // }
        // Optional<Rental> optionalRental = rentalService.getRental(userIdFromToken);
        // Boolean rentalDoesNotExist = optionalRental.isEmpty();
        // if (rentalDoesNotExist) {
        // GlobalExceptionHandler.handleLogicError("Rental ID does not exist",
        // HttpStatus.NOT_FOUND);
        // }
        // // TODO: Verify the userId from the token to the one of the owner_id
        // // TODO: Add logic to update a rental
        // } catch (ApiException ex) {
        // return GlobalExceptionHandler.handleApiException(ex);
        // }
    }

    private void checkTokenUserId(Long userIdFromToken) {
        // Fetch user information based on the user ID
        Optional<Users> optionalSpecificUser = userService.getUserById(userIdFromToken);
        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("User does not exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}

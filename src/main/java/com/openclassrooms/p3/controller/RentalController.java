package com.openclassrooms.p3.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.RentalMapper;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.RentalUpdateRequest;
import com.openclassrooms.p3.payload.response.RentalAllResponse;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;
import com.openclassrooms.p3.payload.response.ResponseMessage;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.RentalService;
import com.openclassrooms.p3.service.S3Service;
import com.openclassrooms.p3.service.UserService;
import com.openclassrooms.p3.utils.JwtUtil;

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
    private UserMapper userMapper;

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

            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);
            // Fetch user information based on the user ID
            verifyAndGetUserByTokenId(userIdFromToken);

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
            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);
            // Fetch user information based on the user ID
            verifyAndGetUserByTokenId(userIdFromToken);

            Rental rental = verifyAndGetRentalById(id);
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
            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);
            verifyAndGetUserByTokenId(userIdFromToken);

            var imageUrl = picture == null ? null : s3Service.uploadFile(picture, "images");

            RentalUpdateRequest request = new RentalUpdateRequest(name, surface, price, description, imageUrl,
                    userIdFromToken);

            rentalService.saveRental(request);

            ResponseMessage responseMessage = new ResponseMessage("Success!");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return GlobalExceptionHandler.handleApiException((ApiException) ex);
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
        try {
            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);
            verifyAndGetUserByTokenId(userIdFromToken);

            Rental oldRental = verifyAndGetRentalById(id);
            checkUserIdMismatch(userIdFromToken, oldRental.getOwnerId());

            // return ResponseEntity.status(HttpStatus.OK).body(oldRental);

            var imageUrl = oldRental.getPicture() == null ? null : oldRental.getPicture();

            RentalUpdateRequest updatedRental = new RentalUpdateRequest(name, surface, price, description, imageUrl,
                    userIdFromToken);
            rentalService.updateRental(oldRental, updatedRental);

            return ResponseEntity.status(HttpStatus.OK).body(updatedRental);

        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Retrieves the user ID from the authorization header.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return The user ID extracted from the JWT token.
     */
    private Long getUserIdFromAuthorizationHeader(String authorizationHeader) {
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

        Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        if (hasJwtExtractionError) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return optionalUserIdFromToken.get();
    }

    /**
     * Retrieves the user information as a DTO entity based on the user ID extracted
     * from the JWT
     * token.
     * 
     * @param userIdFromToken The user ID extracted from the JWT token.
     * @return The user information as a UserInfoResponse object.
     * @throws ApiException If the user with the given ID does not exist or if there
     *                      is a mismatch between the user ID and the token.
     */
    private UserInfoResponse verifyAndGetUserByTokenId(Long userIdFromToken) {
        // Fetch user information based on the user ID
        Optional<Users> optionalSpecificUser = userService.getUserById(userIdFromToken);
        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        Users user = optionalSpecificUser.get();
        // Convert user information to DTO
        UserInfoResponse userEntity = userMapper.toDtoUser(user);

        return userEntity;
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param rentalId The ID of the rental to retrieve.
     * @return The rental with the given ID.
     * @throws ApiException if the rental with the given ID does not exist.
     */
    private Rental verifyAndGetRentalById(Long rentalId) {
        Optional<Rental> optionalRental = rentalService.getRental(rentalId);
        Boolean rentalDoesNotExist = optionalRental.isEmpty();
        if (rentalDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }
        return optionalRental.get();
    }

    /**
     * Checks if the user ID extracted from the authorization token and the user ID
     * extracted from the request are different.
     * If they are different, throws a logic error with the message "Forbidden" and
     * the HTTP status code 403 (FORBIDDEN).
     *
     * @param userIdFromToken   The user ID extracted from the authorization token.
     * @param userIdFromRequest The user ID extracted from the request.
     */
    private void checkUserIdMismatch(Long userIdFromToken, Long userIdFromRequest) {
        Boolean hasUserIdMismatch = userIdFromToken != userIdFromRequest;
        if (hasUserIdMismatch) {
            GlobalExceptionHandler.handleLogicError("Forbidden",
                    HttpStatus.FORBIDDEN);
        }
    }
}

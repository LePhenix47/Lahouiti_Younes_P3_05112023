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

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller for handling rental-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals")
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
    @Operation(description = "Retrieves all rentals", summary = "Retrieves all rentals", responses = {
            @ApiResponse(description = "Successfully retrieved all rentals", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RentalSingleResponse.class)), examples = @ExampleObject(value = "rentals:[{\"id\":1,\"name\":\"Example Rental\",\"surface\":100,\"price\":1000.00,\"picture\":\"example.jpg\",\"description\":\"Example description\",\"owner_id\":1,\"created_at\":\"2023-01-01T12:00:00\",\"updated_at\":\"2023-01-01T13:00:00\"}]"))
            }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
    })
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
    @Operation(description = "Retrieves a rental by its ID", summary = "Retrieves a rental by its ID", responses = {
            @ApiResponse(description = "Successfully retrieved all rentals", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RentalSingleResponse.class)), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Example Rental\",\"surface\":100,\"price\":1000.00,\"picture\":\"example.jpg\",\"description\":\"Example description\",\"owner_id\":1,\"created_at\":\"2023-01-01T12:00:00\",\"updated_at\":\"2023-01-01T13:00:00\"}"))
            }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
            @ApiResponse(description = "User not found", responseCode = "404"),
    })
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
    /**
     * Adds a new rental to the system.
     *
     * @param name                The name of the rental.
     * @param surface             The surface area of the rental.
     * @param price               The price of the rental.
     * @param description         The description of the rental.
     * @param picture             An optional picture of the rental.
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return A response entity with the success status and a response message.
     */
    @PostMapping("")
    @Operation(description = "Adds a new rental", summary = "Adds a new rental", responses = {
            @ApiResponse(description = "Successfully added a new rental", responseCode = "201", content =

            {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Success!\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
            @ApiResponse(description = "Bad form data values", responseCode = "403"),
    })
    public ResponseEntity<?> addRental(@RequestParam("name") String name,
            @Valid @RequestParam("surface") Integer surface,
            @Valid @RequestParam("price") BigDecimal price,
            @Valid @RequestParam("description") String description,
            @Valid @RequestParam(value = "picture", required = false) MultipartFile picture,
            @Valid @RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);
            verifyAndGetUserByTokenId(userIdFromToken);

            String imageUrl = picture == null ? null : s3Service.uploadFile(picture, "images");

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
     * @param id                  The ID of the rental to update in the URL
     * @param name                The new name of the rental in the Form Data
     * @param surface             The new surface area of the rental in the Form
     *                            Data
     * @param price               The new price of the rental in the Form Data
     * @param description         The new description of the rental in the Form Data
     * @param authorizationHeader The authorization header containing the user's
     *                            token
     * @return ResponseEntity<ResponseMessage> with information about the rental
     *         update.
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates information about a specific rental", summary = "Updates a rental by its ID", responses = {
            @ApiResponse(description = "Successfully updated the rental by its ID", responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Success!\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
            @ApiResponse(description = "Rental not found", responseCode = "404"),
            @ApiResponse(description = "Forbidden", responseCode = "403"),
    })
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

            String imageUrl = oldRental.getPicture();

            RentalUpdateRequest updatedRental = new RentalUpdateRequest(name, surface, price, description, imageUrl,
                    userIdFromToken);
            rentalService.updateRental(oldRental, updatedRental);

            ResponseMessage responseMessage = new ResponseMessage("Success!");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);

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

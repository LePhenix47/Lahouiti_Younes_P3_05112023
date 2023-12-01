package com.openclassrooms.p3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.mapper.UserMapper;
import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.request.MessageRequest;
import com.openclassrooms.p3.payload.response.ResponseMessage;
import com.openclassrooms.p3.payload.response.UserInfoResponse;
import com.openclassrooms.p3.service.MessageService;
import com.openclassrooms.p3.service.RentalService;
import com.openclassrooms.p3.service.UserService;
import com.openclassrooms.p3.utils.JwtUtil;

import jakarta.validation.Valid;

/**
 * Controller for handling message-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private MessageService messageService;

    /**
     * Posts a message.
     *
     * @param request The message request containing details.
     * @return
     * @return ResponseEntity<ResponseMessage> with information about
     *         the message post.
     */
    @PostMapping("")
    public ResponseEntity<?> postMessage(@Valid @RequestBody MessageRequest request, BindingResult bindingResult,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            checkBodyPayloadErrors(bindingResult);

            Long userIdFromToken = getUserIdFromAuthorizationHeader(authorizationHeader);

            verifyAndGetUserByTokenId(userIdFromToken);

            checkUserIdMismatch(userIdFromToken, request.user_id());

            verifyAndGetRentalById(request.rental_id());

            messageService.saveMessage(request);

            ResponseMessage response = new ResponseMessage("Successfully created a new message!");
            // Return the saved user with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Checks if there is a mismatch between the user ID extracted from the JWT
     * token and the user ID provided in the request.
     *
     * @param userIdFromToken (Long) The user ID extracted from the JWT token.
     * @param requestId       (Long) The user ID provided in the request.
     */
    private void checkUserIdMismatch(Long userIdFromToken, Long requestId) {
        Boolean hasUserIdMismatch = userIdFromToken != requestId;
        if (hasUserIdMismatch) {
            GlobalExceptionHandler.handleLogicError("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Checks if there are any payload errors in the request body.
     *
     * @param bindingResult The BindingResult object that holds the validation
     *                      errors.
     */
    private void checkBodyPayloadErrors(BindingResult bindingResult) {
        Boolean payloadIsInvalid = bindingResult.hasErrors();
        if (payloadIsInvalid) {
            GlobalExceptionHandler.handlePayloadError("Bad payload", bindingResult, HttpStatus.BAD_REQUEST);
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
            GlobalExceptionHandler.handleLogicError("An unexpected client error occurred", HttpStatus.UNAUTHORIZED);
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
            GlobalExceptionHandler.handleLogicError("Rental ID does not exist",
                    HttpStatus.NOT_FOUND);
        }

        return optionalRental.get();
    }
}

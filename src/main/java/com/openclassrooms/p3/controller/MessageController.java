package com.openclassrooms.p3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.configuration.JwtUtil;
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
    @PostMapping("/")
    public ResponseEntity<?> postMessage(@Valid @RequestBody MessageRequest request, BindingResult bindingResult,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // ! Need to refactor this from these lines: 59-94
            Boolean payloadIsInvalid = bindingResult.hasErrors();
            if (payloadIsInvalid) {
                GlobalExceptionHandler.handlePayloadError("Bad payload", bindingResult, HttpStatus.BAD_REQUEST);
            }
            // Extract JWT from Authorization header
            String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

            // Extract user ID from JWT
            Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

            Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
            if (hasJwtExtractionError) {
                GlobalExceptionHandler.handleLogicError(
                        "JWT could not extract the userId",
                        HttpStatus.UNAUTHORIZED);
            }

            Long userIdFromToken = optionalUserIdFromToken.get();
            // Fetch user information based on the user ID
            Optional<Users> optionalUser = userService.getUserById(userIdFromToken);

            Boolean userDoesNotExist = optionalUser.isEmpty();
            if (userDoesNotExist) {
                GlobalExceptionHandler.handleLogicError("User does not exist",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Users user = optionalUser.get();
            // Convert user information to DTO
            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            Boolean hasUserIdMismatch = userEntity.id() != userIdFromToken;
            if (hasUserIdMismatch) {
                GlobalExceptionHandler.handleLogicError("User ID mismatch",
                        HttpStatus.FORBIDDEN);
            }
            // !

            Optional<Rental> optionalRental = rentalService.getRental(request.rental_id());
            Boolean rentalDoesNotExist = optionalRental.isEmpty();
            if (rentalDoesNotExist) {
                GlobalExceptionHandler.handleLogicError("Rental ID does not exist",
                        HttpStatus.FORBIDDEN);
            }

            messageService.saveMessage(request);

            ResponseMessage response = new ResponseMessage("Successfully created a new message!");
            // Return the saved user with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }
}

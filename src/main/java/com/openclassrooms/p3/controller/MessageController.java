package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.exception.ApiException;
import com.openclassrooms.p3.exception.GlobalExceptionHandler;
import com.openclassrooms.p3.payload.request.MessageRequest;
import com.openclassrooms.p3.service.MessageService;

import jakarta.validation.Valid;

/**
 * Controller for handling message-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
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
    public ResponseEntity<?> postMessage(@Valid @RequestBody MessageRequest request, BindingResult bindingResult) {
        try {
            Boolean payloadIsInvalid = bindingResult.hasErrors();
            if (payloadIsInvalid) {
                GlobalExceptionHandler.handlePayloadError("Bad payload", bindingResult, HttpStatus.BAD_REQUEST);
            }

            // Return the saved user with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }
}

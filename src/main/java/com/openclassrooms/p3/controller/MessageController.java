package com.openclassrooms.p3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.p3.dto.MessageRequest;
import com.openclassrooms.p3.dto.ResponseMessage;
import com.openclassrooms.p3.service.MessageService;

/**
 * Controller for handling message-related operations.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * Posts a message.
     *
     * @param request The message request containing details.
     * @return ResponseEntity with information about the message post.
     */
    @PostMapping("/")
    public ResponseMessage postMessage(@RequestBody MessageRequest request) {
        // TODO: Implement postMessage logic
    }
}

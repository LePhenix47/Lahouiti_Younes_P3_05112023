package com.openclassrooms.p3.dto;

import lombok.Value;

/**
 * Request payload for posting a message.
 */
@Value
public class MessageRequest {
    private Long rental_id;
    private Long user_id;
    private String message;
}

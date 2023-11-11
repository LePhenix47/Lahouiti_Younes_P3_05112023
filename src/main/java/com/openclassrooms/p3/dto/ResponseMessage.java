package com.openclassrooms.p3.dto;

import lombok.Value;

/**
 * Response payload for posting a message.
 */
@Value
public class ResponseMessage {
    private String message;
}

package com.openclassrooms.p3.dto;

import lombok.Value;

@Value
public class MessageRequest {
    private Long rental_id;
    private Long user_id;
    private String message;
}

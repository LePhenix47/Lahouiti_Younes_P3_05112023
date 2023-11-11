package com.openclassrooms.p3.dto;

import lombok.Value;

import java.sql.Date;

@Value
public class MessageResponse {
    private Long id;
    private String name;
    private String email;
    private Date created_at;
    private Date updated_at;
}

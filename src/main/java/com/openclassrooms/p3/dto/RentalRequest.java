package com.openclassrooms.p3.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class RentalRequest {
    private MultipartFile picture;
    private String message;
}

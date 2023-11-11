package com.openclassrooms.p3.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Value;

/**
 * Request payload containing the Form Data for updating a rental.
 */
@Value
public class RentalUpdateRequest {
    private String name;
    private Integer surface;
    private Double price;
    private MultipartFile picture;
    private String description;
}

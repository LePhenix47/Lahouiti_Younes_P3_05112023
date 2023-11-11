package com.openclassrooms.p3.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Value;

@Value
public class RentalSingleResponse {
    private Long id;
    private String name;
    private Integer surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private Long owner_id;
    private Date created_at;
    private Date updated_at;
}

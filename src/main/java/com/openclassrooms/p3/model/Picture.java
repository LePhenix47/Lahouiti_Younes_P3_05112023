package com.openclassrooms.p3.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @Column(name = "picture_url")
    private String pictureUrl;
}

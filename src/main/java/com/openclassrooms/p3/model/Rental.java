package com.openclassrooms.p3.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a rental property.
 */
@Entity
@Data
@Table(name = "rentals")
public class Rental {
    /**
     * Primary key and unique identifier for the rental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * _User who owns the rental. This field won't be added to the database.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, insertable = false, updatable = false)
    private Users owner;

    /**
     * Foreign key referencing the ID of the owner in the `users` table.
     */
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    // @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // private List<Message> messages;

    /**
     * Name of the rental property.
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Surface area of the rental property.
     */
    @Column(name = "surface")
    private Integer surface;

    /**
     * Rental price of the property.
     */
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * File path or URL to a picture of the rental property.
     */
    @Column(name = "picture", columnDefinition = "TEXT")
    private String picture;

    /**
     * Description of the rental property.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Timestamp indicating when the rental property was created.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the rental property.
     */
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

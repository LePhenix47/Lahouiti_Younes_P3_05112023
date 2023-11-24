package com.openclassrooms.p3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a message.
 */
@Entity
@Data
@Table(name = "messages")
public class Message {
    /**
     * Unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * _User who sent the message. This field won't be added to the database.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Users user;

    /**
     * Foreign key referencing the ID of the user in the `users` table.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Rental associated with the message. This field won't be added to the
     * database.
     */
    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false, insertable = false, updatable = false)
    private Rental rental;

    /**
     * Foreign key referencing the ID of the rental in the `rentals` table.
     */
    @Column(name = "rental_id", nullable = false)
    private Long rentalId;

    /**
     * Textual content of the message.
     */
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    /**
     * Timestamp indicating when the message was created.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the message.
     */
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;
}

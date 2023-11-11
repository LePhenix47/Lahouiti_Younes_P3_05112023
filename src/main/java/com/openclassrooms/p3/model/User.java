package com.openclassrooms.p3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity representing a user in the system.
 */
@Entity
@Data
@Table(name = "users")
public class User {
    /**
     * Primary key and unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the user.
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Email address of the user.
     *
     * This field is unique and serves as a means of identification for the user.
     */
    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    /**
     * Password associated with the user's account.
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Timestamp indicating when the user account was created.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update time of the user account.
     */
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;
}

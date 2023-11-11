package com.openclassrooms.p3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.p3.model.Rental;

/**
 * Repository interface for managing Rental entities in the database.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}

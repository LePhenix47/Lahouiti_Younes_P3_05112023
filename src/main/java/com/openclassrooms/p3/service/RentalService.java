package com.openclassrooms.p3.service;

import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.Data;

/**
 * Service class for managing rentals.
 */
@Data
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    /**
     * Retrieve a rental by its unique identifier.
     *
     * @param id The identifier of the rental.
     * @return An Optional containing the rental if found, or empty if not.
     */
    public Optional<Rental> getRental(final Long id) {
        return rentalRepository.findById(id);
    }

    /**
     * Retrieve all rentals.
     *
     * @return Iterable collection of all rentals.
     */
    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Delete a rental by its unique identifier.
     *
     * @param id The identifier of the rental to be deleted.
     */
    public void deleteRental(final Long id) {
        rentalRepository.deleteById(id);
    }

    /**
     * Save or update a rental.
     *
     * @param rental The rental to be saved or updated.
     * @return The saved or updated rental.
     */
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}

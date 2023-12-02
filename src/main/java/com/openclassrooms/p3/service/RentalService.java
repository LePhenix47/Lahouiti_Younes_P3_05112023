package com.openclassrooms.p3.service;

import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.payload.request.RentalUpdateRequest;
import com.openclassrooms.p3.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private S3Service s3Service;

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
     * Saves a rental.
     *
     * @param rentalUpdateRequest The rental request containing details.
     * @return The saved or updated rental.
     */
    public Rental saveRental(RentalUpdateRequest rentalUpdateRequest) {
        Rental rental = new Rental();

        // Populate rental object with data from rentalUpdateRequest
        rental.setName(rentalUpdateRequest.name());
        rental.setSurface(rentalUpdateRequest.surface());
        rental.setPrice(rentalUpdateRequest.price());
        rental.setPicture(rentalUpdateRequest.picture());
        rental.setDescription(rentalUpdateRequest.description());

        rental.setOwnerId(rentalUpdateRequest.owner_id());

        LocalDateTime currentTime = LocalDateTime.now();
        rental.setCreatedAt(currentTime);
        rental.setUpdatedAt(currentTime);

        return rentalRepository.save(rental);
    }

    /**
     * Updates a rental object with new values provided in the RentalUpdateRequest
     * object.
     *
     * @param oldRental The existing rental object to be updated.
     * @param newRental The object containing the new values for the
     *                  rental.
     * @return The updated rental object.
     */
    public Rental updateRental(Rental oldRental, RentalUpdateRequest newRental) {
        Rental existingRental = new Rental();

        existingRental.setId(oldRental.getId());
        existingRental.setName(newRental.name());
        existingRental.setSurface(newRental.surface());
        existingRental.setPrice(newRental.price());
        existingRental.setPicture(newRental.picture());
        existingRental.setDescription(newRental.description());

        existingRental.setOwnerId(newRental.owner_id());

        existingRental.setCreatedAt(oldRental.getCreatedAt());

        LocalDateTime currentTime = LocalDateTime.now();
        existingRental.setUpdatedAt(currentTime);

        return rentalRepository.save(existingRental);
    }
}

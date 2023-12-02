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
     * Updates a rental.
     *
     * @param rental The rental to be saved or updated.
     * @return The saved or updated rental.
     */
    public Rental updateRental(Rental rentalUpdateRequest) {
        Rental existingRental = new Rental();

        existingRental.setId(rentalUpdateRequest.getId());
        existingRental.setName(rentalUpdateRequest.getName());
        existingRental.setSurface(rentalUpdateRequest.getSurface());
        existingRental.setPrice(rentalUpdateRequest.getPrice());
        existingRental.setPicture(rentalUpdateRequest.getPicture());
        existingRental.setDescription(rentalUpdateRequest.getDescription());

        existingRental.setOwnerId(rentalUpdateRequest.getOwnerId());

        // Add additional logic or validation as needed
        existingRental.setCreatedAt(rentalUpdateRequest.getCreatedAt());

        LocalDateTime currentTime = LocalDateTime.now();
        existingRental.setUpdatedAt(currentTime);

        return rentalRepository.save(existingRental);
    }
}

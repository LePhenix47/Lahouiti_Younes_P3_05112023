package com.openclassrooms.p3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This interface represents a mapper for converting Rental objects to
 * RentalSingleResponse objects.
 * It includes mappings and a default method for converting a Rental object to a
 * RentalSingleResponse object.
 */
@Mapper(componentModel = "spring")
public interface RentalMapper {

    /**
     * Converts a Rental object to a RentalSingleResponse object.
     *
     * @param rental The Rental object to be converted.
     * @return The converted RentalSingleResponse object.
     */
    @Mappings({
            @Mapping(target = "owner_id", source = "ownerId"),
            @Mapping(target = "created_at", source = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "updated_at", source = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    RentalSingleResponse toDtoRental(Rental rental);

    /**
     * Converts a list of Rental objects to a list of RentalSingleResponse objects.
     *
     * @param rentals The list of Rental objects to be converted.
     * @return The converted list of RentalSingleResponse objects.
     */
    Iterable<RentalSingleResponse> toDtoRentals(Iterable<Rental> rentals);

    /**
     * Converts a list of Rental objects to a list of RentalSingleResponse objects.
     *
     * @param rentals The list of Rental objects to be converted.
     * @return The converted list of RentalSingleResponse objects.
     */
    @Mappings({
            @Mapping(target = "rentals", source = "rentals")
    })
    List<RentalSingleResponse> toDtoRentalsList(List<Rental> rentals);

    /**
     * Maps a LocalDateTime object to a formatted string representation.
     *
     * @param date The LocalDateTime object to be mapped.
     * @return The formatted string representation of the LocalDateTime object.
     */
    default String map(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatter);
    }
}

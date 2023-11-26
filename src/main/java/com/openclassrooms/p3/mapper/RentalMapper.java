package com.openclassrooms.p3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mappings({
            @Mapping(target = "owner_id", source = "ownerId"),
            @Mapping(target = "created_at", source = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "updated_at", source = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    RentalSingleResponse toDtoRental(Rental rental);

    Iterable<RentalSingleResponse> toDtoRentals(Iterable<Rental> rentals);

    @Mappings({
            @Mapping(target = "rentals", source = "rentals")
    })
    List<RentalSingleResponse> toDtoRentalsList(List<Rental> rentals);

    default String map(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatter);
    }
}

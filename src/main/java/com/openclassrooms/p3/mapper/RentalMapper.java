package com.openclassrooms.p3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.payload.response.RentalAllResponse;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mappings({
            @Mapping(target = "owner_id", source = "ownerId"),
            @Mapping(target = "created_at", source = "createdAt"),
            @Mapping(target = "updated_at", source = "updatedAt")
    })
    RentalSingleResponse toDtoRental(Rental rental);

    Iterable<RentalSingleResponse> toDtoRentals(Iterable<Rental> rentals);
}

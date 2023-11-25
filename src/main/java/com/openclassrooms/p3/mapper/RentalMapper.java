package com.openclassrooms.p3.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.p3.model.Rental;
import com.openclassrooms.p3.payload.response.RentalAllResponse;
import com.openclassrooms.p3.payload.response.RentalSingleResponse;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "id", ignore = false)
    @Mapping(target = "owner_id", source = "ownerId")
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    RentalSingleResponse toDtoRental(Rental request);

    // @Mapping(target = "id", ignore = false)
    // @Mapping(target = "created_at", source = "createdAt")
    // @Mapping(target = "updated_at", source = "updatedAt")
    // RentalAllResponse toDtoRentals(List<Rental> request);

}

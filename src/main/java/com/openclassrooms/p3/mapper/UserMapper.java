package com.openclassrooms.p3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.response.UserInfoResponse;

/**
 * This interface represents a UserMapper, which is responsible for mapping a
 * Users object to a UserInfoResponse object using MapStruct.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a Users object to a UserInfoResponse object.
     * 
     * @param request The Users object to be mapped.
     * @return The mapped UserInfoResponse object.
     */
    @Mapping(target = "id", ignore = false)
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    UserInfoResponse toDtoUser(Users request);

}

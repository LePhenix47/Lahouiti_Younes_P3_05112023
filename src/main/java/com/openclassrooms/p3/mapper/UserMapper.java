package com.openclassrooms.p3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.p3.model.Users;
import com.openclassrooms.p3.payload.response.UserInfoResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = false)
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    UserInfoResponse toDtoUser(Users request);

}

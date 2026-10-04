package com.devteria.springboot.mapper;

import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.request.UserUpdateRequest;
import com.devteria.springboot.dto.response.UserDto;
import com.devteria.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Map ngược lại từ DTO sang Entity
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreateRequest userCreateRequest);


    @Mapping(target = "roles", ignore = true)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);

//    @Mapping(source = "", target = "")
//    @Mapping(target = "", ignore = true)
    UserDto toUserDto(User user);
//    // Cập nhật thông tin từ Request DTO vào Entity đã tồn tại
//    void setUserDto(User user, @MappingTarget UserDto userDto);
}

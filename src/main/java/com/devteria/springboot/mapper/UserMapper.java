package com.devteria.springboot.mapper;

import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.request.UserUpdateRequest;
import com.devteria.springboot.dto.response.UserDto;
import com.devteria.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Map ngược lại từ DTO sang Entity
    User toUser(UserCreateRequest userCreateRequest);
    User fromUserUpdateRequestToUser(UserUpdateRequest userUpdateRequest);
    UserDto toUserDto(User user);
//    // Cập nhật thông tin từ Request DTO vào Entity đã tồn tại
//    void setUserDto(User user, @MappingTarget UserDto userDto);
}

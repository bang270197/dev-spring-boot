package com.devteria.springboot.service;

import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.UserDto;
import com.devteria.springboot.dto.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {
    UserDto createUser(UserCreateRequest request);
    List<UserDto> getUsers();
    UserDto getUserById(String id);
    UserDto updateUser(String id, UserUpdateRequest userDto);
    void deleteUser(String id);
}

package com.devteria.springboot.service;

import com.devteria.springboot.dto.UserDto;
import com.devteria.springboot.dto.UserUpdateRequest;
import com.devteria.springboot.entity.User;

import java.util.List;

public interface IUserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getUsers();
    UserDto getUserById(String id);
    UserDto updateUser(String id, UserUpdateRequest userDto);
    void deleteUser(String id);
}

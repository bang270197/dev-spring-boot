package com.devteria.springboot.service.impl;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.response.UserDto;
import com.devteria.springboot.dto.request.UserUpdateRequest;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.mapper.UserMapper;
import com.devteria.springboot.repository.UserRepository;
import com.devteria.springboot.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class UserServiceImpl implements IUserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserDto createUser(UserCreateRequest request) {
        log.info("Creating user with username: {}", request.getUserName());
        if (userRepository.existsByUserName(request.getUserName())) {
            log.warn("User already exists with username: {}", request.getUserName());
            throw new ResourceNotFoundException(ErrorCode.USER_EXIST);
        }
        User user = userMapper.toUser(request);
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}", savedUser.getId());
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("Fetching all users");
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList(); // Hoặc .collect(Collectors.toList())
        log.info("Fetched {} users", users.size());
        return users;
    }

    @Override
    public UserDto getUserById(String id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                });
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(String id, UserUpdateRequest request) {
        log.info("Updating user with id: {}", id);
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update with id: {}", id);
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                });

        userToUpdate = userMapper.fromUserUpdateRequestToUser(request);

        User updatedUser = userRepository.save(userToUpdate);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return userMapper.toUserDto(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        log.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for delete with id: {}", id);
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                });
        userRepository.delete(user);
        log.info("User deleted successfully with id: {}", id);
    }

}

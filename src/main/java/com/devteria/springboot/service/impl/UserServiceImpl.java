package com.devteria.springboot.service.impl;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.UserDto;
import com.devteria.springboot.dto.request.UserUpdateRequest;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.repository.UserRepository;
import com.devteria.springboot.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserCreateRequest request) {
        log.info("Creating user with username: {}", request.getUserName());
        if (userRepository.existsByUserName(request.getUserName())) {
            log.warn("User already exists with username: {}", request.getUserName());
            throw new ResourceNotFoundException(ErrorCode.USER_EXIST);
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword()); // Trong thực tế nên mã hóa bằng PasswordEncoder
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}", savedUser.getId());
        return mapUserToDto(savedUser);
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("Fetching all users");
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(this::mapUserToDto)
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
        return mapUserToDto(user);
    }

    @Override
    public UserDto updateUser(String id, UserUpdateRequest request) {
        log.info("Updating user with id: {}", id);
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update with id: {}", id);
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                });

        userToUpdate.setPassword(request.getPassword());
        userToUpdate.setUserName(request.getUserName());
        userToUpdate.setFirstName(request.getFirstName());
        userToUpdate.setLastName(request.getLastName());
        userToUpdate.setEmail(request.getEmail());

        User updatedUser = userRepository.save(userToUpdate);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return mapUserToDto(updatedUser);
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

    private UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        // Bảo mật: Không nên gán password vào DTO trả về cho client
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}

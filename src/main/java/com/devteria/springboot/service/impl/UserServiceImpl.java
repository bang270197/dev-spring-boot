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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserCreateRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ResourceNotFoundException(ErrorCode.USER_EXIST);
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword()); // Trong thực tế nên mã hóa bằng PasswordEncoder
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        return mapUserToDto(savedUser);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserToDto)
                .toList(); // Hoặc .collect(Collectors.toList())
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        return mapUserToDto(user);
    }

    @Override
    public UserDto updateUser(String id, UserUpdateRequest request) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        userToUpdate.setPassword(request.getPassword());
        userToUpdate.setUserName(request.getUserName());
        userToUpdate.setFirstName(request.getFirstName());
        userToUpdate.setLastName(request.getLastName());
        userToUpdate.setEmail(request.getEmail());

        User updatedUser = userRepository.save(userToUpdate);
        return mapUserToDto(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
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

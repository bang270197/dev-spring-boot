package com.devteria.springboot.controller;

import com.devteria.springboot.common.BaseController;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.UserDto;
import com.devteria.springboot.dto.request.UserUpdateRequest;
import com.devteria.springboot.dto.response.ApiResponse;
import com.devteria.springboot.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserCreateRequest request) {
        return created(userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        return success(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable String userId) {
        return success(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable String userId,
                                                           @Valid @RequestBody UserUpdateRequest request) {
        return success(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return noContent();
    }
}


package com.devteria.springboot.controller;

import com.devteria.springboot.common.BaseController;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.response.UserDto;
import com.devteria.springboot.dto.request.UserUpdateRequest;
import com.devteria.springboot.dto.response.ApiResponse;
import com.devteria.springboot.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Slf4j
public class UserController extends BaseController {

    IUserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserCreateRequest request) {
        return created(userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();

        String authorities = authentication.getAuthorities().stream().map(
                GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        log.info("==> User đang thao tác: {}", userName);
        log.info("==> Các quyền (Roles) được cấp: [{}]", authorities);

        return success(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable String userId) {
        return success(userService.getUserById(userId));
    }

    @GetMapping("/myInfo")
    public ResponseEntity<ApiResponse<UserDto>> getMyInfo() {
        return success(userService.myInfo());
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


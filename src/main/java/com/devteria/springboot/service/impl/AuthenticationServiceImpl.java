package com.devteria.springboot.service.impl;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.dto.request.AuthenticationRequest;
import com.devteria.springboot.dto.response.AuthenticationResponse;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.repository.UserRepository;
import com.devteria.springboot.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {
    UserRepository repository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        User user = repository.findByUserName(request.getUserName()).orElseThrow(
                () -> {
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                }
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean matches = passwordEncoder.matches(request.getPassWord(), user.getPassword());
        response.setAuthenticated(matches);
        return response;
    }
}

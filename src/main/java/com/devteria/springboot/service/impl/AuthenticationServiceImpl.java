package com.devteria.springboot.service.impl;

import com.devteria.springboot.enums.ErrorCode;
import com.devteria.springboot.dto.request.AuthenticationRequest;
import com.devteria.springboot.dto.request.IntrospectRequest;
import com.devteria.springboot.dto.response.AuthenticationResponse;
import com.devteria.springboot.dto.response.IntrospectResponse;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.repository.UserRepository;
import com.devteria.springboot.security.JwtTokenProvider;
import com.devteria.springboot.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class AuthenticationServiceImpl implements IAuthenticationService {
    UserRepository repository;
    JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authentication attempt for username: {}", request.getUserName());
        AuthenticationResponse response = new AuthenticationResponse();
        User user = repository.findByUserName(request.getUserName()).orElseThrow(
                () -> {
                    log.warn("Authentication failed: user not found for username: {}", request.getUserName());
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                }
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean matches = passwordEncoder.matches(request.getPassWord(), user.getPassword());

        if (!matches) {
            log.warn("Authentication failed: invalid credentials for username: {}", request.getUserName());
            throw new ResourceNotFoundException(ErrorCode.UNAUTHENTICATED);
        }

        String token = jwtTokenProvider.generateToken(user);

        response.setAuthenticated(matches);
        response.setToken(token);
        log.info("Authentication succeeded for username: {}", request.getUserName());
        return response;
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        IntrospectResponse response = new IntrospectResponse();
        var token = request.getToken();
        boolean valid = jwtTokenProvider.verify(token);
        response.setValid(valid);
        log.info("Token introspection completed: valid={}", valid);
        return response;
    }
}

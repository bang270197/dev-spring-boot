package com.devteria.springboot.service.impl;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.dto.request.AuthenticationRequest;
import com.devteria.springboot.dto.request.IntrospectRequest;
import com.devteria.springboot.dto.response.AuthenticationResponse;
import com.devteria.springboot.dto.response.IntrospectResponse;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.repository.UserRepository;
import com.devteria.springboot.security.JwtTokenProvider;
import com.devteria.springboot.service.IAuthenticationService;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
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
    JwtTokenProvider jwtTokenProvider;

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

        if (!matches)
            throw new ResourceNotFoundException(ErrorCode.UNAUTHENTICATED);

        String token = jwtTokenProvider.generateToken(user.getUserName());

        response.setAuthenticated(matches);
        response.setToken(token);
        return response;
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        IntrospectResponse response = new IntrospectResponse();
        var token = request.getToken();
        boolean valid = jwtTokenProvider.verify(token);
        response.setValid(valid);
        return response;
    }
}

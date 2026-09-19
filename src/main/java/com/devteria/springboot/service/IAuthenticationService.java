package com.devteria.springboot.service;

import com.devteria.springboot.dto.request.AuthenticationRequest;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.response.AuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

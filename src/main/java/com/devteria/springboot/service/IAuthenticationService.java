package com.devteria.springboot.service;

import com.devteria.springboot.dto.request.AuthenticationRequest;
import com.devteria.springboot.dto.request.IntrospectRequest;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.response.AuthenticationResponse;
import com.devteria.springboot.dto.response.IntrospectResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request);
}

package com.devteria.springboot.controller;

import com.devteria.springboot.common.BaseController;
import com.devteria.springboot.dto.request.AuthenticationRequest;
import com.devteria.springboot.dto.request.IntrospectRequest;
import com.devteria.springboot.dto.response.ApiResponse;
import com.devteria.springboot.dto.response.AuthenticationResponse;
import com.devteria.springboot.dto.response.IntrospectResponse;
import com.devteria.springboot.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class AuthenticationController extends BaseController {

    IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @RequestBody AuthenticationRequest request) {
        return success(authenticationService.authenticate(request));
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(
            @RequestBody IntrospectRequest request) {
        return success(authenticationService.introspect(request));
    }
}

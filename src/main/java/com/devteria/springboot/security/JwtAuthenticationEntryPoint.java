package com.devteria.springboot.security;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.dto.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode.getCode(),
                errorCode.getMessage()
                );


        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
    }
}

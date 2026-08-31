package com.devteria.springboot.common;

import com.devteria.springboot.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity
                .ok(ApiResponse.success(data, ErrorCode.SUCCESS));
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(data, ErrorCode.CREATED));
    }

    protected ResponseEntity<ApiResponse<Void>> noContent() {
        return ResponseEntity
                .ok(ApiResponse.success(ErrorCode.SUCCESS));
    }
}

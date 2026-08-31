package com.devteria.springboot.exception;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.common.Result;
import com.devteria.springboot.dto.response.ApiResponse;
import com.devteria.springboot.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionGobalHandle {

    // Bắt lỗi nghiệp vụ chủ động ném ra bằng AppException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(ResourceNotFoundException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse<Void> response = ApiResponse.error(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    // Bắt lỗi Validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .result(Result.fail(ErrorCode.INVALID_KEY.getCode(), ErrorCode.INVALID_KEY.getMessage()))
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // Bắt các lỗi hệ thống không lường trước
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.UNCATEGORIZED_EXCEPTION.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


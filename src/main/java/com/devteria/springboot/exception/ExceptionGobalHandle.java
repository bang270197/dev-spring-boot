package com.devteria.springboot.exception;

import com.devteria.springboot.enums.ErrorCode;
import com.devteria.springboot.common.Result;
import com.devteria.springboot.dto.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.devteria.springboot.enums.ErrorCode.UNAUTHORIZED;

@ControllerAdvice
@Log4j2
public class ExceptionGobalHandle {

    // Bắt lỗi nghiệp vụ chủ động ném ra bằng AppException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(ResourceNotFoundException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("Business request failed: errorCode={}, message={}", errorCode.getCode(), errorCode.getMessage());
        ApiResponse<Void> response = ApiResponse.error(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    // Bắt lỗi Validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ErrorCode errorCode;
            try {
                errorCode = ErrorCode.valueOf(error.getDefaultMessage());
            } catch (IllegalArgumentException | NullPointerException ignored) {
                errorCode = ErrorCode.INVALID_KEY;
            }
            errors.put(error.getField(), errorCode.getMessage());
        }
        log.warn("Request validation failed for fields: {}", errors.keySet());

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .result(Result.fail(ErrorCode.INVALID_KEY.getCode(), ErrorCode.INVALID_KEY.getMessage()))
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // Bắt lỗi nghiệp vụ chủ động ném ra bằng AppException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(
                UNAUTHORIZED.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(UNAUTHORIZED.getHttpStatus()).body(response);
    }

    // Bắt các lỗi hệ thống không lường trước
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        log.error("Unhandled exception", ex);
        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.UNCATEGORIZED_EXCEPTION.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


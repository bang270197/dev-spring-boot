package com.devteria.springboot.dto.response;

import com.devteria.springboot.common.ErrorCode;
import com.devteria.springboot.common.Result;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private Result result;
    private T data;

    public static <T> ApiResponse<T> success(T data, ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .result(Result.success(errorCode))
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .result(Result.success(errorCode))
                .build();
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .result(Result.fail(code, message))
                .build();
    }
}

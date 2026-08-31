package com.devteria.springboot.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private int code;
    private String message;
    private boolean success;
    private LocalDateTime timestamp;

    public static Result success(ErrorCode errorCode) {
        return Result.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Result fail(int code, String message) {
        return Result.builder()
                .code(code)
                .message(message)
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
    }
}

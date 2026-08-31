package com.devteria.springboot.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    int status;
    String message;
    LocalDateTime timestamp;
    Map<String, String> errors; // Dùng để chứa thông tin lỗi validation chi tiết (nếu có)
}

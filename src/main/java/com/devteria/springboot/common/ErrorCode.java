package com.devteria.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(1000, "Thành công", HttpStatus.OK),
    CREATED(1001, "Tạo mới thành công", HttpStatus.CREATED),

    // Client Errors (4xx)
    INVALID_KEY(1002, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    //existsByUserName
    USER_EXIST(1004, "UserName đã tồn tại", HttpStatus.BAD_REQUEST),

    // Server Errors (5xx)
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống chưa xác định", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatus;
}

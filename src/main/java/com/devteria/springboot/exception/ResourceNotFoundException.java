package com.devteria.springboot.exception;

import com.devteria.springboot.common.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

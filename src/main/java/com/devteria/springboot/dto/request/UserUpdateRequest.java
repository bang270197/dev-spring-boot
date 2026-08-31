package com.devteria.springboot.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}

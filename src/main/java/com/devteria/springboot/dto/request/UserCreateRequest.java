package com.devteria.springboot.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @Size(min = 6, message = "UserName must be at least 6 char")
    private String userName;

    @Size(min = 8, message = "Password must be at least 8 char")
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}

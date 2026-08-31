package com.devteria.springboot.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @Size(min = 6, message = "UserName must be at least 6 char")
    String userName;

    @Size(min = 8, message = "Password must be at least 8 char")
    String password;
    String email;
    String firstName;
    String lastName;
}

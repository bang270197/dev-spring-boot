package com.devteria.springboot.dto.request;

import com.devteria.springboot.annotation.ValidDateOfBirth;
import com.devteria.springboot.entity.Role;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @Size(min = 6, message = "UserName must be at least 6 char")
    String userName;

    @Size(min = 8, message = "Password must be at least 8 char")
    String passWord;
    String email;
    String firstName;
    String lastName;

    @ValidDateOfBirth
    LocalDate dateOfBirth;

    Set<String> roles;

}

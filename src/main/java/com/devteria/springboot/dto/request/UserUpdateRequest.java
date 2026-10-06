package com.devteria.springboot.dto.request;

import com.devteria.springboot.annotation.ValidDateOfBirth;
import com.devteria.springboot.entity.Role;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6, message = "INVALID_USERNAME")
    String userName;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
    String email;
    String firstName;
    String lastName;

    @ValidDateOfBirth(minAge = 21)
    LocalDate dateOfBirth;

    Set<String> roles;
}

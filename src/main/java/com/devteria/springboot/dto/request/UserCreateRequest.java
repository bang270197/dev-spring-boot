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

    @Size(min = 6, message = "INVALID_USERNAME")
    String userName;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String passWord;
    String email;
    String firstName;
    String lastName;

    @ValidDateOfBirth(minAge = 21, message = "INVALID_DATE_OF_BIRTH")
    LocalDate dateOfBirth;

    Set<String> roles;

}

package com.devteria.springboot.dto.request;

import com.devteria.springboot.entity.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    Set<String> roles;
}

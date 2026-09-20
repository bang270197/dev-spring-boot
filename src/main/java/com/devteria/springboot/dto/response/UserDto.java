package com.devteria.springboot.dto.response;

import com.devteria.springboot.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String id;
    String userName;
//    String password;
    String email;
    String firstName;
    String lastName;
    Set<Role> roles;
}

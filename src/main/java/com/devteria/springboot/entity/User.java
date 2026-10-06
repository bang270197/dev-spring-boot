package com.devteria.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @ManyToMany
    Set<Role> roles;
}

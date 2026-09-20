package com.devteria.springboot.configuration;

import com.devteria.springboot.entity.Role;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner initApplicationRunner(UserRepository userRepository) {

        return args -> {
            if (!userRepository.existsByUserName("admin")) {

                Set<Role> roles = new HashSet<>();
                roles.add(Role.ADMIN);
                User user = User.builder()
                        .userName("admin")
                        .firstName("admin")
                        .lastName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles).build();

                userRepository.save(user);
                log.warn("User with name admin has been created");
            }

        };
    }
}

package com.devteria.springboot.repository;

import com.devteria.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserName(String username);
    Optional<User> findByUserName(String username);
}

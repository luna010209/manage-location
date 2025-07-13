package com.example.ManageLocation.repo.auth;

import com.example.ManageLocation.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail (String email);
    Optional<UserEntity> findByPhone (String phone);
}

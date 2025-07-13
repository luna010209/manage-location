package com.example.ManageLocation.service.auth;

import com.example.ManageLocation.dto.auth.SignUpRequest;
import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.repo.auth.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    @Override
    @Transactional
    public Long newUser(SignUpRequest request) {
        if (userRepo.existsByUsername(request.username()))
            throw new CustomException(HttpStatus.CONFLICT, "Username already exists");
        else if (userRepo.existsByEmail(request.email()))
            throw new CustomException(HttpStatus.CONFLICT, "Email already exists");
        else if (userRepo.existsByPhone(request.phone()))
            throw new CustomException(HttpStatus.CONFLICT, "Phone number already exists");
        else if (!request.password().equals(request.cfPassword()))
            throw new CustomException(HttpStatus.CONFLICT, "Password and Confirm password do not match!!");

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .password(encoder.encode(request.password()))
                .email(request.email())
                .phone(request.phone())
                .roles(Set.of(request.role()))
                .build();
        userRepo.save(user);
        return user.getId();
    }
}

package com.example.ManageLocation.currentUser;

import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.repo.auth.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUser {
    private final UserRepo userRepo;
    public UserEntity currentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "No exist user login!")
        );
        return user;
    }
}

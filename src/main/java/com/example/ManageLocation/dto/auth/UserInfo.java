package com.example.ManageLocation.dto.auth;

import com.example.ManageLocation.entity.auth.UserEntity;

public record UserInfo(
        String username,
        String email,
        String phone
) {
    public static UserInfo from(UserEntity user){
        return new UserInfo(
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }
}

package com.example.ManageLocation.dto.auth;

import com.example.ManageLocation.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "Please input your mobile phone number")
        String phone,

        @NotBlank(message = "Please input your email")
        String email,

        @NotBlank(message = "Please input your username")
        String username,

        @NotBlank(message = "Please input your password")
        String password,

        @NotBlank(message = "Please confirm your password")
        String cfPassword,

        Role role


) {
}

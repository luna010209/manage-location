package com.example.ManageLocation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Please input your email or phone")
        String emailOrPhone,

        @NotBlank(message = "Please input your password")
        String password
) {
}

package com.example.ManageLocation.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}

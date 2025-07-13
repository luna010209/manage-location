package com.example.ManageLocation.service.auth;

import com.example.ManageLocation.dto.auth.SignUpRequest;

public interface UserService {
    Long newUser(SignUpRequest request);
}

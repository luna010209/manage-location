package com.example.ManageLocation.service.auth;

import com.example.ManageLocation.dto.auth.LoginRequest;
import com.example.ManageLocation.dto.auth.LoginResponse;
import com.example.ManageLocation.dto.auth.SignUpRequest;
import com.example.ManageLocation.dto.auth.UserInfo;

public interface UserService {
    Long newUser(SignUpRequest request);

    LoginResponse authenticate(LoginRequest request, String ipAddress, String userAgent);

    UserInfo userLogin();
}

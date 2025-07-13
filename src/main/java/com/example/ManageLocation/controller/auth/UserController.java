package com.example.ManageLocation.controller.auth;

import com.example.ManageLocation.dto.auth.SignUpRequest;
import com.example.ManageLocation.service.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpRequest request){
        Long userId = userService.newUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}

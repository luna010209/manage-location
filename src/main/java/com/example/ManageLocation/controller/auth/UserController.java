package com.example.ManageLocation.controller.auth;

import com.example.ManageLocation.dto.auth.LoginRequest;
import com.example.ManageLocation.dto.auth.LoginResponse;
import com.example.ManageLocation.dto.auth.SignUpRequest;
import com.example.ManageLocation.dto.auth.UserInfo;
import com.example.ManageLocation.service.auth.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpRequest request){
        Long userId = userService.newUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse response
    ){
        String userAgent = httpServletRequest.getHeader("User-Agent");

        // Obtain ip of client
        String ipAddress = getClientIp(httpServletRequest);

        LoginResponse loginResponse = userService.authenticate(request, ipAddress, userAgent);
        response.setHeader("Authorization", "Bearer "+ loginResponse.accessToken());
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping
    public ResponseEntity<UserInfo> getUserLogin(){
        return ResponseEntity.ok(userService.userLogin());
    }

    public String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");

        if (ip==null || ip.isEmpty()) ip = request.getHeader("X-FORWARDED-FOR");

        if (ip==null || ip.isEmpty()) ip = request.getRemoteAddr();

        if (StringUtils.isNotBlank(ip)){
            List<String> ips = Arrays.asList(ip.split(","));
            ip = ips.get(0).trim();
        }
        return ip;
    }
}

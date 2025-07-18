package com.example.ManageLocation.service.auth;

import com.example.ManageLocation.dto.auth.LoginRequest;
import com.example.ManageLocation.dto.auth.LoginResponse;
import com.example.ManageLocation.dto.auth.SignUpRequest;
import com.example.ManageLocation.dto.auth.UserInfo;
import com.example.ManageLocation.entity.auth.HistoryLogin;
import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.jwt.CustomUserDetails;
import com.example.ManageLocation.jwt.JwtProperties;
import com.example.ManageLocation.jwt.TokenProvider;
import com.example.ManageLocation.repo.auth.HistoryLoginRepo;
import com.example.ManageLocation.repo.auth.UserRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtProperties jwtProperties;

    private final HistoryLoginRepo historyLoginRepo;
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

    @Override
    @Transactional
    public LoginResponse authenticate(LoginRequest request, String ipAddress, String userAgent) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.emailOrPhone(), request.password());
            log.info("Authentication token: {}", authenticationToken.getPrincipal());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("Name: {}", authentication.getName());

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            UserEntity user = userDetails.getUser();

            user.setLastLogin(LocalDateTime.now());

            HistoryLogin historyLogin = HistoryLogin.of(user, ipAddress, userAgent);
            historyLoginRepo.save(historyLogin);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = TokenProvider.releaseToken(authentication, jwtProperties);
            String refreshToken = TokenProvider.refreshToken(authentication, jwtProperties);

            return new LoginResponse(accessToken, refreshToken);
        } catch (AuthenticationException e){
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Fail to login");
        }
    }

    @Override
    public UserInfo userLogin() {
        String emailOrPhone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = null;
        if (userRepo.existsByEmail(emailOrPhone))
            user = userRepo.findByEmail(emailOrPhone).orElseThrow();
        else if (userRepo.existsByPhone(emailOrPhone)) {
            user = userRepo.findByPhone(emailOrPhone).orElseThrow();
        }
        if (user == null) throw new CustomException(HttpStatus.BAD_REQUEST, "No user login");
        return UserInfo.from(user);
    }

}

package com.example.ManageLocation.config;

import com.example.ManageLocation.jwt.JwtEntryPoint;
import com.example.ManageLocation.jwt.JwtFilter;
import com.example.ManageLocation.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtEntryPoint jwtEntryPoint;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//                        .requestMatchers("").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptHandle -> exceptHandle
                        .authenticationEntryPoint(jwtEntryPoint)
                )
                .addFilterBefore(
                        new JwtFilter(tokenProvider),
                        AuthorizationFilter.class
                ).with(new JwtConfig(tokenProvider), customizer -> {
                });
        return http.build();

    }
}

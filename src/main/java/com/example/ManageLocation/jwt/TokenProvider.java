package com.example.ManageLocation.jwt;

import com.example.ManageLocation.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import static com.example.ManageLocation.jwt.TokenUtils.AUTHORITIES_KEY;

@Component
@Slf4j
public class TokenProvider {
    private final SecretKey key;
    private final CustomUserDetailsService userDetailsService;

    public TokenProvider(JwtProperties jwtProperties, CustomUserDetailsService userDetailsService) {
        this.key = TokenUtils.generateSigningKey(jwtProperties.secretKey());
        this.userDetailsService = userDetailsService;
    }

    public static String releaseToken(
            Authentication authentication,
            JwtProperties jwtProperties
    ) {
        SecretKey signingKey = TokenUtils.generateSigningKey(jwtProperties.secretKey());
        Instant issuedAt = Instant.now();
        Date expirationDate = Date.from(issuedAt.plus(jwtProperties.expirationMinutes(), ChronoUnit.MINUTES));

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(Date.from(issuedAt))
                .expiration(expirationDate)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(signingKey)
                .compact();
    }

    public static String refreshToken(
            Authentication authentication,
            JwtProperties jwtProperties
    ) {
        SecretKey signingKey = TokenUtils.generateSigningKey(jwtProperties.secretKey());
        Instant issuedAt = Instant.now();
        Date expirationDate = Date.from(issuedAt.plus(jwtProperties.refreshExpirationMinutes(), ChronoUnit.MINUTES));

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(Date.from(issuedAt))
                .claim(AUTHORITIES_KEY, authorities)
                .expiration(expirationDate)
                .signWith(signingKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY). toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    public boolean tokenValidate(String token){
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e){
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid Token");
        }
    }
}

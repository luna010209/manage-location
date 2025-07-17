package com.example.ManageLocation.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Component
public class TokenUtils {

    public static final String AUTHORITIES_KEY = "auth";

    public static SecretKey generateSigningKey(String secretKey){
        byte[] encodeKey = Base64.getEncoder().withoutPadding().encode(secretKey.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(encodeKey);
    }
}

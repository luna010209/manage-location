package com.example.ManageLocation.jwt;

import com.example.ManageLocation.entity.auth.UserEntity;
import com.example.ManageLocation.exception.CustomException;
import com.example.ManageLocation.repo.auth.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String phoneOrMail) throws UsernameNotFoundException {
        UserEntity user;
        log.info(phoneOrMail);
        if (userRepo.existsByEmail(phoneOrMail))
            user = userRepo.findByEmail(phoneOrMail).orElseThrow();
        else if (userRepo.existsByPhone(phoneOrMail))
            user = userRepo.findByPhone(phoneOrMail).orElseThrow();
        else throw new CustomException(HttpStatus.NOT_FOUND, "Not found this email or this phone number!");

        return new CustomUserDetails(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities (UserEntity user){
        return user.getRoles().stream()
                .map(role-> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
}

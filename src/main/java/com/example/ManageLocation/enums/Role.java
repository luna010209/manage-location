package com.example.ManageLocation.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_BUSINESS,
    ROLE_BOSS,
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}

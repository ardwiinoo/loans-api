package com.ardwiinoo.loansapi.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    USER,
    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
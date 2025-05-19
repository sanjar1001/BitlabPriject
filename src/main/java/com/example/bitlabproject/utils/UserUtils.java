package com.example.bitlabproject.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Slf4j
public final class UserUtils {

    private UserUtils() {} // Приватный конструктор для утилитного класса

    public static Jwt getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            return jwtAuthToken.getToken();
        } else {
            log.warn("Couldn't extract user: authentication is not JwtAuthenticationToken");
            return null;
        }
    }

    public static String getCurrentUserName() {
        Jwt jwt = getCurrentUser();
        if (jwt != null) {
            return jwt.getClaimAsString("preferred_username"); // Или другой claim, например "sub", если надо ID
        }
        return null;
    }
}

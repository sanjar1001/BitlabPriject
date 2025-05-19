package com.example.bitlabproject.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConvertor implements Converter<Jwt, AbstractAuthenticationToken> {

    public AbstractAuthenticationToken convert(Jwt source) {

        Collection<GrantedAuthority> authorities = extractAuthorities(source);
        return new JwtAuthenticationToken(source, authorities);
    }



    private Collection<GrantedAuthority> extractAuthorities(Jwt source) {

        Map<String, Object> realAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        if (realAccess == null||realAccess.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> roles = (List<String>) realAccess.get("roles");
        if (roles == null||roles.isEmpty()) {
            return Collections.emptyList();
        }

        return roles.stream()
                .map(roleName -> "ROLE_"+roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}

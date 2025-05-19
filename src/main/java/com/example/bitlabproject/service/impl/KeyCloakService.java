package com.example.bitlabproject.service.impl;

import com.example.bitlabproject.dto.CreateDto;
import com.example.bitlabproject.dto.SignDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeyCloakService {

    private final Keycloak keycloak;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KeyCloakService.class);

    @Value("${keycloak.url}")
    private String url;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client}")
    private String client;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @PreAuthorize("hasRole('ADMIN')") // Только ADMIN может вызвать
    public UserRepresentation create(CreateDto user) {

        logger.info("Создание нового пользователя с именем: {}", user.getUsername());

        UserRepresentation newUser = getUserRepresentation(user);

        logger.debug("Создаем пользователя с данными: {}", newUser);

        Response response = keycloak.
                realm(realm).
                users().
                create(newUser);

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            System.out.println("✅ Пользователь успешно создан");
            logger.info("✅ Пользователь успешно создан");

        } else {
            System.out.println("❌ Ошибка при создании: " + response.getStatus());
        }

        List<UserRepresentation> searchUser = keycloak.realm(realm).users().search(newUser.getUsername());
        return searchUser.get(0);

    }

    private static UserRepresentation getUserRepresentation(CreateDto user) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(user.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(Boolean.TRUE);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(user.getPassword());
        cred.setTemporary(false);

        newUser.setCredentials(List.of(cred));
        return newUser;
    }

    public String signIn(SignDto userSignInDto) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("username", userSignInDto.getUsername());
        formData.add("password", userSignInDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            throw new RuntimeException("Failed signIn");
        }

        System.out.println("Keycloak response: " + responseBody);
        return (String) responseBody.get("access_token");
    }


    public void changePassword(String name, String newPassword) {

        System.out.println(name);

        List<UserRepresentation> users = keycloak.realm(realm).users().search(name);

        if (users.isEmpty()) {
            logger.warn("Пользователь не найден!");
        }

        UserRepresentation user = users.get(0);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(newPassword);
        cred.setTemporary(false);

        keycloak.realm(realm).users().get(user.getId()).resetPassword(cred);

        logger.info("Пароль успешно изменен");

    }
    }

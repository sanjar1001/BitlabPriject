package com.example.bitlabproject.controller;

import com.example.bitlabproject.dto.ChangePassword;
import com.example.bitlabproject.dto.CreateDto;
import com.example.bitlabproject.dto.SignDto;
import com.example.bitlabproject.service.CourseService;
import com.example.bitlabproject.service.impl.KeyCloakService;
import com.example.bitlabproject.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CreateUserController {

    private final KeyCloakService keycloak;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')") // Только ADMIN может вызвать
    public ResponseEntity<?> createUser( @RequestBody CreateDto createDto) {
        keycloak.create(createDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('ADMIN')") // Только ADMIN может вызвать
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword) {
        String currentUserName = UserUtils.getCurrentUserName();
        if (currentUserName.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации");
        }

        try {
            keycloak.changePassword(currentUserName, changePassword.getPassword());
            return ResponseEntity.ok("Пороль успешно изменен");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignDto sigDto) {
        return keycloak.signIn(sigDto);
    }



}
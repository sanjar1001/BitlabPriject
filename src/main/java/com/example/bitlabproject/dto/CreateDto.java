package com.example.bitlabproject.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDto {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String role;

}

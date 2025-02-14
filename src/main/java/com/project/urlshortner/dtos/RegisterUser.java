package com.project.urlshortner.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterUser {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}

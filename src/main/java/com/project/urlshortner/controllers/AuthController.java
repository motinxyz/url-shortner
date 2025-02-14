package com.project.urlshortner.controllers;

import com.project.urlshortner.dtos.RegisterUser;
import com.project.urlshortner.models.User;
import com.project.urlshortner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser registerUser){
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(registerUser.getPassword());
        user.setEmail(registerUser.getEmail());
        user.setRole("ROLE_USER");

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}

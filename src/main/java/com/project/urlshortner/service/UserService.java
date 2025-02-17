package com.project.urlshortner.service;

import com.project.urlshortner.dtos.LoginRequest;
import com.project.urlshortner.models.User;
import com.project.urlshortner.repositories.UserRepository;
import com.project.urlshortner.security.jwt.JwtAuthenticationResponse;
import com.project.urlshortner.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest  loginRequest){
//        System.out.println("debug line: UserService.authenticateUser(): username: " + loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword())
        );

//        System.out.println("debug line: authentication successful");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateToken(userDetails);

        return new JwtAuthenticationResponse(jwtToken);

    }

    public User findByUsername(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return user;
    }
}

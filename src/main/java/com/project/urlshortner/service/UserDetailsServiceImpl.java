package com.project.urlshortner.service;

import com.project.urlshortner.models.User;
import com.project.urlshortner.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return UserDetailsImpl.build(user);
//        System.out.println("debug line: Fetching user from database: " + username);
//
//        Optional<User> optionalUser = userRepository.findByUsername(username);

//        if (optionalUser.isEmpty()) {
//            System.out.println("debug line:  User not found in the database: " + username);
//        } else {
//            System.out.println("debug line:  User found: " + optionalUser.get().getUsername() + ", password: " + optionalUser.get().getPassword());
//        }

//        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

//        return UserDetailsImpl.build(user);
    }
}

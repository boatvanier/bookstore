package com.example.bookstore.service;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserJPARepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserJPARepository repository;
    private final AuthenticationManager authenticationManager;

    public UserService(UserJPARepository repository, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }

    public List<User> findAllUsers(){

        return repository.findAll();
    }
    public Optional<User> findUserByUserId(Long userId) {
        return repository.findById(userId);
    }

    public String login(String username, String password) {
         Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        if (authentication.isAuthenticated()) {
            return "JWT";
        }

        return "";

    }
}

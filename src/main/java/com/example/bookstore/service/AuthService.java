package com.example.bookstore.service;

import com.example.bookstore.model.UserAuth;
import com.example.bookstore.repository.UserJPARepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.bookstore.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final UserJPARepository userJPARepository;

    public AuthService(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userJPARepository.findByUserName(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("login failed");
        }
        return new UserAuth(user.get());
    }
}

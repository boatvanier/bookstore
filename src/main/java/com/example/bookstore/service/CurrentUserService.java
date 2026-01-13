package com.example.bookstore.service;

import com.example.bookstore.model.User;
import com.example.bookstore.model.UserAuth;
import com.example.bookstore.repository.UserJPARepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserJPARepository userJPARepository;

    public CurrentUserService(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication !=null && authentication.getPrincipal() instanceof UserAuth user) {
            return userJPARepository.findByUserName(user.getUsername()).get();
        }
        return null;
    }
    public Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication !=null && authentication.getPrincipal() instanceof UserAuth user) {
            return user.getUserId();
        }
        return null;

    }
}

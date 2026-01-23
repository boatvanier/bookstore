package com.example.bookstore.service;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserJPARepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final JwtService jwtService;

    public UserService(UserJPARepository repository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Page<User> findAllUsers(String keyword, int page, int size, String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        if (keyword == null || keyword.isBlank()){
            return repository.findAll(pageable);
        }
        return repository.findByUserNameContainingOrEmailContainingIgnoreCase(keyword, keyword, pageable);
    }
    @Cacheable(value = "users", key = "#userId")
    public Optional<User> findUserByUserId(Long userId) {
        return repository.findById(userId);
    }

    @CacheEvict(value = "users", key="#id")
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @CachePut(value = "users", key="#user.id")
    public User updateUser(User user) {
        return repository.save(user);
    }

    public String login(String username, String password) {
         Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        }

        return "";

    }
}

package com.example.bookstore.controller;

import com.example.bookstore.controller.response.UserResponse;
import com.example.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Users", description = "User management APIs")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(
                service.findAllUsers()
                        .stream()
                        .map(UserResponse::toResponse)
                        .toList()
        );
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(
                service.findUserByUserId(userId)
                        .map(UserResponse::toResponse)
                        .orElse(null)
        );
    }

}
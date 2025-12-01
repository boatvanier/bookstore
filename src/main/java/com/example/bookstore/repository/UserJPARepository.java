package com.example.bookstore.repository;

import com.example.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
    //Lab 2
    //1. Find a user by exact username
    Optional<User> findByUserName(String username);
    //2. Check if an email already exists
    boolean existsByEmailIgnoreCase(String email);
    //3. List all users with a given role
    List<User> findByRole(String role);
    //4. Find users whose email contains a keyword
    List<User> findByEmailContainingIgnoreCase(String keyword);
    //5. Get all users sorted by creation date (descending)
    List<User> findAllByOrderByCreatedAtDesc();
    //6. Find users created after a given date
    List<User> findByCreatedAtAfter(Timestamp date);
}

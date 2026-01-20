package com.example.bookstore.repository;

import com.example.bookstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
    //Lab 2 - JPA Query Method
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


    //JPQL - Java Persistence Query Language
    @Modifying
    @Query("delete from User u where u.email = :email")
    void deleteByEmail(@Param("email") String email);

    //Native Query
    @Modifying
    @Query(value = "delete from users u where u.email = :email", nativeQuery = true)
    void deleteByEmailNative(@Param("email") String email);

    //JPA Query method
    Page<User> findByUserNameContainingOrEmailContainingIgnoreCase(String username, String email, Pageable pageable);

    //JPQL
    @Query("Select u from User u where Lower(u.userName) like Lower(concat('%', :keyword, '%'))" +
            "or Lower(u.email) like Lower(concat('%', :keyword, '%'))")
    User findByKeywords(@Param("keyword") String keyword);
}

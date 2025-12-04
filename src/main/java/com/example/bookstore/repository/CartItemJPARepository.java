package com.example.bookstore.repository;

import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemJPARepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}

package com.example.bookstore.service;

import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemJPARepository;
import com.example.bookstore.repository.UserJPARepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartItemJPARepository cartItemJPARepository;
    private final UserJPARepository userJPARepository;

    public CartService(CartItemJPARepository cartItemJPARepository, UserJPARepository userJPARepository) {
        this.cartItemJPARepository = cartItemJPARepository;
        this.userJPARepository = userJPARepository;
    }

    public List<CartItem> findMyCart(Long userId){
        User user = userJPARepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("No user is not found"));
        return cartItemJPARepository.findByUser(user);
    }

}

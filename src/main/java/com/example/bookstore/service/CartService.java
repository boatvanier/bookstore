package com.example.bookstore.service;

import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookJPARepository;
import com.example.bookstore.repository.CartItemJPARepository;
import com.example.bookstore.repository.UserJPARepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartItemJPARepository cartItemJPARepository;
    private final UserJPARepository userJPARepository;
    private final BookJPARepository bookJPARepository;

    public CartService(CartItemJPARepository cartItemJPARepository, UserJPARepository userJPARepository, BookJPARepository bookJPARepository) {
        this.cartItemJPARepository = cartItemJPARepository;
        this.userJPARepository = userJPARepository;
        this.bookJPARepository = bookJPARepository;
    }

    public List<CartItem> findMyCart(Long userId){
        User user = userJPARepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("No user is found"));
        return cartItemJPARepository.findByUser(user);
    }

    public void createCart(Long userId, Long bookId){
        User user = userJPARepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("No user is found"));
        Book book = bookJPARepository.findById(bookId)
                .orElseThrow(()->new ResourceNotFoundException("no book is found"));

        CartItem cart;
        Optional<CartItem> cartItemOptional = cartItemJPARepository.findByUserAndBook(user, book);
        if (cartItemOptional.isPresent()) {
            cart = cartItemOptional.get();
            cart.setQuantity(cart.getQuantity()+1);
        } else {
            cart = new CartItem(user, book);
        }
        cartItemJPARepository.save(cart);
    }

    public void update(Long userId, Long bookId, int quantity) {
        User user = userJPARepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("No user is found"));
        Book book = bookJPARepository.findById(bookId)
                .orElseThrow(()->new ResourceNotFoundException("no book is found"));
        CartItem cartItem = cartItemJPARepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new ResourceNotFoundException("no cart item is found"));

        cartItem.setQuantity(quantity);
        cartItemJPARepository.save(cartItem);
    }

    public void clearCart(Long userId) {
        User user = userJPARepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("No user is found"));
        cartItemJPARepository.deleteByUser(user);
    }

}

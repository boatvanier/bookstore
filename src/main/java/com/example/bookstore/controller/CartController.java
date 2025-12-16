package com.example.bookstore.controller;


import com.example.bookstore.controller.request.CreateCartRequest;
import com.example.bookstore.controller.request.UpdateCartRequest;
import com.example.bookstore.controller.response.CartItemResponse;
import com.example.bookstore.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/myCart")
@Tag(name = "Carts", description = "Cart management APIs")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getMyCart() {
        return ResponseEntity.ok(
                cartService.findMyCart(1L)
                        .stream().map(CartItemResponse::toResponse)
                        .toList()
        );
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCartItem(@RequestBody @Valid CreateCartRequest cartRequest){
        cartService.createCart(1L, cartRequest.getBookId());
    }

    @PutMapping("/books/{bookId}")
    public void updateCartItem(@PathVariable Long bookId, @RequestBody @Valid UpdateCartRequest cartRequest) {
        cartService.update(1L, bookId, cartRequest.getQuantity());
    }


}

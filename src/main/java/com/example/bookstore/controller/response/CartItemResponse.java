package com.example.bookstore.controller.response;

import com.example.bookstore.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {
    BookResponse book;
    int quantity;

    public static CartItemResponse toResponse(CartItem cartItem) {
        return new CartItemResponse(
                BookResponse.toResponse(cartItem.getBook()),
                cartItem.getQuantity()
        );
    }
}

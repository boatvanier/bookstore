package com.example.bookstore.controller.response;


import com.example.bookstore.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    Long id;
    BookShortResponse book;
    int quantity;
    double price;

    public static OrderItemResponse toResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                BookShortResponse.toResponse(item.getBook()),
                item.getQuantity(),
                item.getPriceAtPurchase()
        );
    }
}

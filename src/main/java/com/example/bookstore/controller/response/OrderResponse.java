package com.example.bookstore.controller.response;

import com.example.bookstore.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    UserResponse user;
    double total;
    String status;
    List<OrderItemResponse> items;

    public static OrderResponse toResponse(Order order){
        return new OrderResponse(
                UserResponse.toResponse(order.getUser()),
                order.getTotalAmount(),
                order.getStatus().toString(),
                order.getOrderItems().stream().map(OrderItemResponse::toResponse).toList()
        );
    }
}

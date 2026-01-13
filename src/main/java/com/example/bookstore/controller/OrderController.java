package com.example.bookstore.controller;

import com.example.bookstore.controller.response.OrderResponse;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(){
        return ResponseEntity.ok(
                orderService.getOrders()
                        .stream().map(OrderResponse::toResponse)
                        .toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder() {
        orderService.placeOrder();
    }

    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@PathVariable Long userId) {
        orderService.placeOrder(userId);
    }
}

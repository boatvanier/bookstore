package com.example.bookstore.controller;

import com.example.bookstore.controller.response.OrderResponse;
import com.example.bookstore.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
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
}

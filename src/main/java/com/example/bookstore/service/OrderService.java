package com.example.bookstore.service;

import com.example.bookstore.model.Order;
import com.example.bookstore.repository.OrderJPARepository;
import com.example.bookstore.repository.UserJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final UserJPARepository userJPARepository;


    public OrderService(OrderJPARepository orderJPARepository, UserJPARepository userJPARepository) {
        this.orderJPARepository = orderJPARepository;
        this.userJPARepository = userJPARepository;
    }

    public List<Order> getOrders(){
        return orderJPARepository.findAll();
    }
}

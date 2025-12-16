package com.example.bookstore.service;

import com.example.bookstore.model.*;
import com.example.bookstore.repository.BookJPARepository;
import com.example.bookstore.repository.CartItemJPARepository;
import com.example.bookstore.repository.OrderJPARepository;
import com.example.bookstore.repository.UserJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final UserJPARepository userJPARepository;
    private final CartItemJPARepository cartItemJPARepository;
    private final BookJPARepository bookJPARepository;


    public OrderService(OrderJPARepository orderJPARepository, UserJPARepository userJPARepository, CartItemJPARepository cartItemJPARepository, BookJPARepository bookJPARepository) {
        this.orderJPARepository = orderJPARepository;
        this.userJPARepository = userJPARepository;
        this.cartItemJPARepository = cartItemJPARepository;
        this.bookJPARepository = bookJPARepository;
    }

    public List<Order> getOrders(){
        return orderJPARepository.findAll();
    }

    public void placeOrder() {
        placeOrder(1L);
    }

    @Transactional
    public void placeOrder(Long userId){
        User user = userJPARepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("user is not found"));
        List<CartItem> cartItems = cartItemJPARepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items are selected");
        }

        Order order = new Order();
        order.setUser(user);

        double total =0;

        for(CartItem item: cartItems) {
            Book book = item.getBook();
            //verify stock
            if(book.getStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough remaining");
            }
            //reduce stock
            book.setStock(book.getStock() - item.getQuantity()) ;
            bookJPARepository.save(book);

            //add order item
            order.addItem(new OrderItem(book, item.getQuantity(), book.getPrice()));
            total += item.getQuantity()* book.getPrice();
        }

        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PLACED);

        orderJPARepository.save(order);
        //clear cart
        cartItemJPARepository.deleteByUser(user);
    }
}

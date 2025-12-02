package com.example.bookstore.controller.request;

import lombok.Data;

@Data // all getters, setters, toString(), equals(), AllArgsConstructor, etc...
public class BookRequest {
    private String title;
    private String author;
    private double price;
    private int stock;
    private String image;
}

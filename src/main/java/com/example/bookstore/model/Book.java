package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private String image;

    public Book(String title, String author, Double price, Integer stock, String image) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }
}

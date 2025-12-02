package com.example.bookstore.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // all getters, setters, toString(), equals(), AllArgsConstructor, etc...
public class BookRequest {

    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    @Size(min=2, max = 50, message = "title must be between 2 and 50 letters")
    private String title;

    @NotNull(groups = UpdateGroup.class)
    private String author;
    private double price;
    private int stock;
    private String image;
}

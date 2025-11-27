package com.example.bookstore.controller.response;

import com.example.bookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class BookResponse {
    Long bookId;
    String bookName;
    String authorName;
    double price;
    int stock;
    String image;

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock(),
                book.getImage()
        );
    }
}

package com.example.bookstore.controller.response;

import com.example.bookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    int likes;
    List<UserResponse> likedUsers;

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock(),
                book.getImage(),
                book.getLikes().size(),
                book.getLikes().stream().map(UserResponse::toResponse).toList()
        );
    }
}

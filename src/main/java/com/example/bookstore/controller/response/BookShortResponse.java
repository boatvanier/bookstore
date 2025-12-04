package com.example.bookstore.controller.response;


import com.example.bookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class BookShortResponse {
    Long bookId;
    String bookName;
    String image;

    public static BookShortResponse toResponse(Book book){
        return new BookShortResponse(
                book.getId(),
                book.getTitle(),
                book.getImage()
        );
    }
}

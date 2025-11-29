package com.example.bookstore.controller;

import com.example.bookstore.controller.response.BookResponse;
import com.example.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks(@RequestParam(name="title", required = false)String title) {
        return ResponseEntity.ok(bookService.getBooks(title)
                .stream()
                .map(BookResponse::toResponse)
                .toList());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getBook(bookId)
                .map(BookResponse::toResponse)
                .orElse(null)
        );
    }
}

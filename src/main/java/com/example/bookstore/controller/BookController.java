package com.example.bookstore.controller;

import com.example.bookstore.controller.request.BookRequest;
import com.example.bookstore.controller.response.BookResponse;
import com.example.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody BookRequest bookRequest){
        bookService.createBook(bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getPrice(),
                bookRequest.getStock(),
                bookRequest.getImage());
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@PathVariable Long bookId, @RequestBody BookRequest bookRequest){
        bookService.updateBook(bookId,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getPrice(),
                bookRequest.getStock(),
                bookRequest.getImage());
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
    }
}

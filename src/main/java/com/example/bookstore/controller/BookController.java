package com.example.bookstore.controller;

import com.example.bookstore.controller.request.BookRequest;
import com.example.bookstore.controller.request.CreateGroup;
import com.example.bookstore.controller.request.LikeBookRequest;
import com.example.bookstore.controller.request.UpdateGroup;
import com.example.bookstore.controller.response.BookResponse;
import com.example.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management APIs")
@CrossOrigin(origins = "http://localhost:3000")
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
    public void createBook(@RequestBody @Validated(CreateGroup.class) BookRequest bookRequest){
        bookService.createBook(bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getPrice(),
                bookRequest.getStock(),
                bookRequest.getImage());
    }

    @PostMapping("/{bookId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public void likeBook(@PathVariable Long bookId, @RequestBody @Valid LikeBookRequest likeBookRequest){
        bookService.likeBook(bookId, likeBookRequest.getUserId());
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@PathVariable Long bookId, @RequestBody @Validated(UpdateGroup.class) BookRequest bookRequest){
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

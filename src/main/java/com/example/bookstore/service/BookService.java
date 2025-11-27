package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookJPARepository bookJPARepository;

    public BookService(BookJPARepository bookJPARepository){
        this.bookJPARepository = bookJPARepository;
    }

    public List<Book> getBooks(String title){
        if (title == null || title.isBlank())
            return bookJPARepository.findAll();

        return bookJPARepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Book> getBook(Long bookId) {
        return bookJPARepository.findById(bookId);
    }
}

package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookJPARepository bookJPARepository;

    public BookService(BookJPARepository bookJPARepository){
        this.bookJPARepository = bookJPARepository;
    }

    public List<Book> getBooks(){
        return bookJPARepository.findAll();
    }
}

package com.example.bookstore.service;

import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookJPARepository;
import com.example.bookstore.repository.UserJPARepository;
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

    public void createBook(String title, String author, Double price, Integer stock, String image) {
        bookJPARepository.save(
                new Book(title, author,price, stock, image)
        );
    }

    public void updateBook(Long bookId, String title, String author, Double price, Integer stock, String image) {
        Book book = bookJPARepository
                .findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book is not found"));
        book.setTitle(title);
        book.setAuthor(author);
        book.setStock(stock);
        book.setPrice(price);
        book.setImage(image);
        bookJPARepository.save(book);
    }

    public void deleteBook(Long bookId){
        bookJPARepository.deleteById(bookId);
    }

    public void likeBook(Long bookId, Long userId){}
}

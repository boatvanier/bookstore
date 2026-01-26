package com.example.bookstore.service;

import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
class BookServiceTest {
    private BookJPARepository bookJPARepository;
    private BookService bookService;

    @BeforeEach
    void setup() {
        bookJPARepository = mock(BookJPARepository.class);
        bookService = new BookService(bookJPARepository);
    }

    @Test
    void testGetBooks_withTitle() {
        List<Book> mockBooks = Arrays.asList(
                new Book("Spring in Action", "Craig Walls", 40.0, 10, "image1"),
                new Book("Spring Boot Cookbook", "Alex Antonov", 35.0, 5, "image2")
        );

        when(bookJPARepository.findByTitleContainingIgnoreCase("spring")).thenReturn(mockBooks);

        List<Book> result = bookService.getBooks("spring");
        assertEquals(2, result.size());
        verify(bookJPARepository).findByTitleContainingIgnoreCase("spring");
    }

    @Test
    void testGetBooks_withoutTitle() {
        List<Book> allBooks = Arrays.asList(
                new Book("Book1", "Author1", 20.0, 5, "img1"),
                new Book("Book2", "Author2", 25.0, 8, "img2")
        );

        when(bookJPARepository.findAll()).thenReturn(allBooks);

        List<Book> result = bookService.getBooks(null);
        assertEquals(2, result.size());
        verify(bookJPARepository).findAll();
    }

    @Test
    void testGetBook_found() {
        Book book = new Book("Book1", "Author1", 20.0, 5, "img1");
        when(bookJPARepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBook(1L);
        assertTrue(result.isPresent());
        assertEquals("Book1", result.get().getTitle());
    }

    @Test
    void testGetBook_notFound() {
        when(bookJPARepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Book> result = bookService.getBook(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateBook() {
        bookService.createBook("New Book", "Author", 30.0, 10, "img");

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookJPARepository).save(captor.capture());

        Book savedBook = captor.getValue();
        assertEquals("New Book", savedBook.getTitle());
        assertEquals("Author", savedBook.getAuthor());
        assertEquals(30.0, savedBook.getPrice());
        assertEquals(10, savedBook.getStock());
        assertEquals("img", savedBook.getImage());
    }

    @Test
    void testUpdateBook_existing() {
        Book book = new Book("Old Title", "Old Author", 20.0, 5, "oldImg");
        when(bookJPARepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.updateBook(1L, "New Title", "New Author", 25.0, 8, "newImg");

        verify(bookJPARepository).save(book);
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals(25.0, book.getPrice());
        assertEquals(8, book.getStock());
        assertEquals("newImg", book.getImage());
    }

    @Test
    void testUpdateBook_notFound() {
        when(bookJPARepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.updateBook(1L, "Title", "Author", 20.0, 5, "img");
        });

        assertEquals("Book is not found", exception.getMessage());
    }

    @Test
    void testDeleteBook() {
        bookService.deleteBook(1L);
        verify(bookJPARepository).deleteById(1L);
    }
}

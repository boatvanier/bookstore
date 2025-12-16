package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJpaRepositoryTest {

    @Autowired
    private BookJPARepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll(); // clean DB before each test
    }

    // --------------------------------------------------------------
    // 1. CREATE & READ TEST
    // --------------------------------------------------------------
    @Test
    void saveBook_ShouldPersistBook() {
        Book book = new Book("Title A", "Author A", 10.0, 5, "");
        Book saved = repository.save(book);

        assertNotNull(saved.getId());
        assertEquals("Title A", saved.getTitle());
    }

    // --------------------------------------------------------------
    // 2. FIND BY ID TEST
    // --------------------------------------------------------------
    @Test
    void findById_ShouldReturnCorrectBook() {
        Book book = repository.save(new Book("T1", "A1", 9.99, 10, ""));

        Book found = repository.findById(book.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("T1", found.getTitle());
    }

    // --------------------------------------------------------------
    // 3. FIND BY TITLE CONTAINING
    // --------------------------------------------------------------
    @Test
    void findByTitleContaining_ShouldReturnMatchingBooks() {
        repository.save(new Book("Spring Guide", "A1", 12.0, 10, ""));
        repository.save(new Book("Spring Recipes", "A2", 18.0, 3, ""));
        repository.save(new Book("Java Basics", "A3", 15.0, 7, ""));

        List<Book> results = repository.findByTitleContainingIgnoreCase("Spring");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(b -> b.getTitle().contains("Guide")));
        assertTrue(results.stream().anyMatch(b -> b.getTitle().contains("Recipes")));
    }

    // --------------------------------------------------------------
    // 4. FIND BY TITLE CONTAINING — Negative Case
    // --------------------------------------------------------------
    @Test
    void findByTitleContaining_NoMatch_ShouldReturnEmptyList() {
        repository.save(new Book("ABC", "X", 1.0, 2, ""));
        repository.save(new Book("DEF", "Y", 1.0, 3, ""));

        List<Book> results = repository.findByTitleContainingIgnoreCase("XYZ");

        assertTrue(results.isEmpty());
    }

    // --------------------------------------------------------------
    // 5. UPDATE TEST
    // --------------------------------------------------------------
    @Test
    void updateBook_ShouldModifyPersistedData() {
        Book book = repository.save(new Book("Old Title", "A1", 9.0, 10, ""));

        book.setTitle("New Title");
        book.setStock(50);

        Book updated = repository.save(book);

        assertEquals("New Title", updated.getTitle());
        assertEquals(50, updated.getStock());
    }

    // --------------------------------------------------------------
    // 6. DELETE TEST
    // --------------------------------------------------------------
    @Test
    void deleteBook_ShouldRemoveFromDatabase() {
        Book book = repository.save(new Book("T1", "A1", 10.0, 5, ""));
        Long id = book.getId();

        repository.delete(book);

        assertFalse(repository.findById(id).isPresent());
    }

    // --------------------------------------------------------------
    // 7. MANY-TO-MANY likes TEST
    // --------------------------------------------------------------
    @Test
    void bookLikes_ShouldSaveUsersThatLikedBook() {

        // Mocking simple User entity with ID only (assuming minimal User exists)
        User user1 = new User();
        user1.setId(100L);

        User user2 = new User();
        user2.setId(200L);

        Book book = new Book("Like Test", "A1", 10.0, 10, "");
        book.setLikes(Set.of(user1, user2));

        Book saved = repository.save(book);

        assertNotNull(saved.getId());
        assertNotNull(saved.getLikes());
        assertEquals(2, saved.getLikes().size());
    }

}

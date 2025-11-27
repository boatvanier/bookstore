package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJPARepository extends JpaRepository<Book, Long> {
}

package com.newSpring.bookservice.modal.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import com.newSpring.bookservice.dto.BookDto;
import com.newSpring.bookservice.modal.BookModal;

public interface BookRepo extends JpaRepository<BookModal, Long> {
    // You can add custom query methods here if needed
    BookModal findByName(String name);

    @NativeQuery("SELECT name,id FROM books")
    List<BookDto> findAllBooks();

    List<BookModal> findByAuthorId(Long userId);

}
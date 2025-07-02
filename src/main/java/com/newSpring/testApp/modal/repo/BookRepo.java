package com.newSpring.testApp.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.testApp.modal.BookModal;

public interface BookRepo extends JpaRepository<BookModal, Integer> {
    // You can add custom query methods here if needed
    BookModal findByName(String name);
}
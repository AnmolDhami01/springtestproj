package com.newSpring.bookservice.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.bookservice.modal.JwtModal;

public interface JwtRepo extends JpaRepository<JwtModal, Integer> {
    JwtModal findByUserId(Long id);
}

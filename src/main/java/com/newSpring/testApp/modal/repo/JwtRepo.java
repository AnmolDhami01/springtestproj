package com.newSpring.testApp.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.testApp.modal.JwtModal;

public interface JwtRepo extends JpaRepository<JwtModal, Integer> {
    JwtModal findByUserId(Long id);
}

package com.newSpring.bookservice.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.bookservice.modal.UserModal;

public interface UsersRepo extends JpaRepository<UserModal, Integer> {
    UserModal findByName(String name);

    UserModal findById(Long id);
}

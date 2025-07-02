package com.newSpring.testApp.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.testApp.modal.UserModal;

public interface UsersRepo extends JpaRepository<UserModal, Integer> {
    UserModal findByName(String name);

    UserModal findById(Long id);
}

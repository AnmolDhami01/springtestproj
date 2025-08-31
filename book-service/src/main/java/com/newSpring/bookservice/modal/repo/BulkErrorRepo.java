package com.newSpring.bookservice.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.bookservice.modal.BulkErrorModal;

public interface BulkErrorRepo extends JpaRepository<BulkErrorModal, Integer> {

}

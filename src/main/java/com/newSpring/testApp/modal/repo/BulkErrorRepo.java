package com.newSpring.testApp.modal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.testApp.modal.BulkErrorModal;

public interface BulkErrorRepo extends JpaRepository<BulkErrorModal, Integer> {

}

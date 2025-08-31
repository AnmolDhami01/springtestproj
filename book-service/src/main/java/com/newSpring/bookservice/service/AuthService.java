package com.newSpring.bookservice.service;

import com.newSpring.bookservice.RequestEntity.LoginRequest;
import com.newSpring.bookservice.ResponseEntinty.ResponseWrapper;
import com.newSpring.bookservice.modal.UserModal;

import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseWrapper> login(LoginRequest loginRequest);

    ResponseEntity<ResponseWrapper> register(UserModal userModal);
}

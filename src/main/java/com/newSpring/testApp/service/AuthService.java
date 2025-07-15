package com.newSpring.testApp.service;

import com.newSpring.testApp.RequestEntity.LoginRequest;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.modal.UserModal;

import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseWrapper> login(LoginRequest loginRequest);

    ResponseEntity<ResponseWrapper> register(UserModal userModal);
}

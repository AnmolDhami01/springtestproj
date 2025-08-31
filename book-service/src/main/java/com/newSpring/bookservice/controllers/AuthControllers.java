package com.newSpring.bookservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newSpring.bookservice.RequestEntity.LoginRequest;
import com.newSpring.bookservice.ResponseEntinty.ResponseWrapper;
import com.newSpring.bookservice.modal.UserModal;
import com.newSpring.bookservice.service.AuthService;

@RestController
@RequestMapping("/auth/v1")
public class AuthControllers {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> register(@RequestBody UserModal userModal) {
        return authService.register(userModal);
    }
}

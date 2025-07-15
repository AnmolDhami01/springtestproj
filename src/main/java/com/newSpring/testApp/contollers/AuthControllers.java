package com.newSpring.testApp.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newSpring.testApp.RequestEntity.LoginRequest;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.service.AuthService;

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

package com.newSpring.testApp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.newSpring.testApp.RequestEntity.LoginRequest;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.modal.JwtModal;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.modal.repo.JwtRepo;
import com.newSpring.testApp.modal.repo.UsersRepo;
import com.newSpring.testApp.service.AuthService;
import com.newSpring.testApp.utils.JwtUtil;
import java.util.Date;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtRepo jwtRepo;

    @Override
    public ResponseEntity<ResponseWrapper> login(LoginRequest loginRequest) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        try {
            UserModal user = usersRepo.findByName(loginRequest.getName());
            if (user == null) {
                statusDescription.setStatusCode(401);
                statusDescription.setStatusDescription("Invalid username or password");
                responseWrapper.setStatusDescriptions(statusDescription);
                return ResponseEntity.ok(responseWrapper);
            }
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                statusDescription.setStatusCode(401);
                statusDescription.setStatusDescription("Invalid username or password");
                responseWrapper.setStatusDescriptions(statusDescription);
                return ResponseEntity.ok(responseWrapper);
            }
            String generateToken = this.jwtUtil.generateToken(user.getName());

            JwtModal findByUserId = this.jwtRepo.findByUserId(user.getId());

            if (findByUserId == null) {

                JwtModal jwtModal = new JwtModal();
                jwtModal.setUser(user);
                jwtModal.setExpiryDate(new Date(System.currentTimeMillis() + jwtUtil.EXPIRATION_TIME));
                jwtModal.setJwtToken(generateToken);
                JwtModal savedToken = this.jwtRepo.save(jwtModal);
                user.setUserTokenDetails(savedToken);
            } else {
                findByUserId.setExpiryDate(new Date());
                findByUserId.setJwtToken(generateToken);
                JwtModal savedToken = this.jwtRepo.save(findByUserId);
                user.setUserTokenDetails(savedToken);
            }
            statusDescription.setStatusCode(200);
            statusDescription.setStatusDescription("Login successful");
            responseWrapper.setStatusDescriptions(statusDescription);
            responseWrapper.setUser(user);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal server error");
            responseWrapper.setStatusDescriptions(statusDescription);
            return ResponseEntity.ok(responseWrapper);
        }

    }

    @Override
    public ResponseEntity<ResponseWrapper> register(UserModal userModal) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        try {
            UserModal findUser = usersRepo.findByName(userModal.getName());
            if (findUser != null) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("User already exists");
                responseWrapper.setStatusDescriptions(statusDescription);
                return ResponseEntity.ok(responseWrapper);
            }

            userModal.setPassword(passwordEncoder.encode(userModal.getPassword()));
            usersRepo.save(userModal);
            statusDescription.setStatusCode(200);
            statusDescription.setStatusDescription("User registered successfully");
            responseWrapper.setStatusDescriptions(statusDescription);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal server error");
            responseWrapper.setStatusDescriptions(statusDescription);
            return ResponseEntity.ok(responseWrapper);
        }
    }

}

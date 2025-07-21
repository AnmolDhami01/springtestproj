package com.newSpring.testApp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.constant.ConstantManager;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("users/")
@CrossOrigin(value = "*")
public class UsersControllers {

    @Autowired
    private UserService userService;

    @Autowired
    private ConstantManager constantManager;

    @GetMapping("v1/getUsers")
    public ResponseEntity<ResponseWrapper> getUsers() {
        ResponseWrapper responseWrapper1 = new ResponseWrapper();
        StatusDescription statusDescription1 = new StatusDescription();

        responseWrapper1.setStatusDescriptions(statusDescription1);
        responseWrapper1 = this.userService.getUsers();

        return new ResponseEntity<>(responseWrapper1, HttpStatus.OK);
    }

    @PostMapping("v1/addUser")
    public ResponseEntity<ResponseWrapper> addUser(@RequestBody UserModal userModal) {
        ResponseWrapper responseWrapper1 = new ResponseWrapper();
        StatusDescription statusDescription1 = new StatusDescription();

        responseWrapper1.setStatusDescriptions(statusDescription1);

        try {
            responseWrapper1 = this.userService.addUser(userModal);
            statusDescription1 = responseWrapper1.getStatusDescriptions();
            responseWrapper1.setStatusDescriptions(statusDescription1);
        } catch (Exception e) {
            statusDescription1.setStatusCode(500);
            statusDescription1.setStatusDescription("Internal Server Error");
            responseWrapper1.setStatusDescriptions(statusDescription1);
        }

        return new ResponseEntity<>(responseWrapper1, HttpStatus.OK);

    }

    @GetMapping("v1/getPlaceholderUsers")
    public ResponseEntity<ResponseWrapper> getPlaceholderUsers() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {
            responseWrapper = this.userService.getPlaceholderUsers();
        } catch (Exception e) {
            statusDescription.setStatusCode(constantManager.Error.getStatusCode());
            statusDescription.setStatusDescription(constantManager.Error.getStatusDescription());
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);

    }

}
package com.newSpring.testApp.service;

import java.util.List;

import com.newSpring.testApp.RequestEntity.CreateBook;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.modal.UserModal;

public interface UserService {
    ResponseWrapper getUsers();

    ResponseWrapper addUser(UserModal userModal);

    ResponseWrapper getPlaceholderUsers();

}

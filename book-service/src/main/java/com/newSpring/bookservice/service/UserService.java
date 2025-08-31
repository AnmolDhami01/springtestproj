package com.newSpring.bookservice.service;

import java.util.List;

import com.newSpring.bookservice.RequestEntity.CreateBook;
import com.newSpring.bookservice.ResponseEntinty.ResponseWrapper;
import com.newSpring.bookservice.modal.UserModal;

public interface UserService {
    ResponseWrapper getUsers();

    ResponseWrapper addUser(UserModal userModal);

    ResponseWrapper getPlaceholderUsers();

}

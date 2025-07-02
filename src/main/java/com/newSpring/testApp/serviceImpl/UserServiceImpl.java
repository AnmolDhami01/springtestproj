package com.newSpring.testApp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newSpring.testApp.RequestEntity.CreateBook;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.modal.repo.UsersRepo;
import com.newSpring.testApp.modal.repo.BookRepo;
import com.newSpring.testApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private BookRepo booksRepo;

    @Override
    public ResponseWrapper getUsers() {
        try {
            StatusDescription statusDescription = new StatusDescription(200, "Success");
            List<UserModal> users = usersRepo.findAll();

            ResponseWrapper responseWrapper = new ResponseWrapper(statusDescription, users);
            return responseWrapper;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching users", e);
        }
    }

    @Override
    public ResponseWrapper addUser(UserModal userModal) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper(statusDescription, null);
        try {
            UserModal findUser = usersRepo.findByName(userModal.getName());
            if (findUser != null) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("User already exists");
                responseWrapper.setStatusDescriptions(statusDescription);
                return responseWrapper;
            }

            usersRepo.save(userModal);
            statusDescription.setStatusCode(200);
            statusDescription.setStatusDescription("Success");
            responseWrapper.setStatusDescriptions(statusDescription);

        } catch (Exception e) {
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error");
            responseWrapper.setStatusDescriptions(statusDescription);

        } finally {
            return responseWrapper;
        }
    }

}

package com.newSpring.bookservice.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.newSpring.bookservice.ResponseEntinty.ResponseWrapper;
import com.newSpring.bookservice.ResponseEntinty.StatusDescription;
import com.newSpring.bookservice.config.RestTemplateConfig;
import com.newSpring.bookservice.modal.UserModal;
import com.newSpring.bookservice.modal.repo.UsersRepo;
import com.newSpring.bookservice.modal.repo.BookRepo;
import com.newSpring.bookservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private BookRepo booksRepo;

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @Override
    public ResponseWrapper getUsers() {
        try {
            StatusDescription statusDescription = new StatusDescription(200, "Success");
            List<UserModal> users = usersRepo.findAll();

            ResponseWrapper responseWrapper = new ResponseWrapper();
            responseWrapper.setStatusDescriptions(statusDescription);
            responseWrapper.setUsers(users);
            return responseWrapper;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching users", e);
        }
    }

    @Override
    public ResponseWrapper addUser(UserModal userModal) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper();
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

            return responseWrapper;

        } catch (Exception e) {
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error");
            responseWrapper.setStatusDescriptions(statusDescription);
            return responseWrapper;
        }
    }

    @Override
    public ResponseWrapper getPlaceholderUsers() {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatusDescriptions(statusDescription);
        try {

            String url = "https://jsonplaceholder.typicode.com/users";
            ResponseEntity<Object[]> response = restTemplateConfig.restTemplate().getForEntity(url, Object[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                statusDescription.setStatusCode(200);
                statusDescription.setStatusDescription("Success");
                Object[] users = response.getBody();
                List<Object> usersList = Arrays.asList(users);
                responseWrapper.setPlaceholderUsers(usersList);
            } else {
                statusDescription.setStatusCode(500);
                statusDescription.setStatusDescription("Internal Server Error");
            }

        } catch (Exception e) {
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error");
        }

        return responseWrapper;
    }

}

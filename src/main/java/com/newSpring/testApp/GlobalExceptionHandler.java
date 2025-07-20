package com.newSpring.testApp;

import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ConcurrentModificationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        statusDescription.setStatusCode(400);
        statusDescription.setStatusDescription(
                "Invalid JSON format.");

        responseWrapper.setStatusDescriptions(statusDescription);

        return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        statusDescription.setStatusCode(403);
        statusDescription.setStatusDescription("Access Denied: You don't have permission to access this resource");

        responseWrapper.setStatusDescriptions(statusDescription);

        return new ResponseEntity<>(responseWrapper, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConcurrentModificationException.class)
    public ResponseEntity<ResponseWrapper> handleConcurrentModificationException(ConcurrentModificationException ex) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        statusDescription.setStatusCode(500);
        statusDescription
                .setStatusDescription("Internal Server Error: JSON serialization failed due to circular references");

        responseWrapper.setStatusDescriptions(statusDescription);

        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseWrapper> handleAuthenticationException(AuthenticationException ex) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        statusDescription.setStatusCode(401);
        statusDescription.setStatusDescription("Authentication failed: " + ex.getMessage());

        responseWrapper.setStatusDescriptions(statusDescription);

        return new ResponseEntity<>(responseWrapper, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleGenericException(Exception ex) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        statusDescription.setStatusCode(500);
        statusDescription.setStatusDescription("Internal Server Error: " + ex.getMessage());

        responseWrapper.setStatusDescriptions(statusDescription);

        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
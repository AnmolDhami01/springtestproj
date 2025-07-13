package com.newSpring.testApp;

import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(HttpMessageNotReadableException.class)
    // public ResponseEntity<ResponseWrapper>
    // handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    // ResponseWrapper responseWrapper = new ResponseWrapper();
    // StatusDescription statusDescription = new StatusDescription();

    // statusDescription.setStatusCode(400);
    // statusDescription.setStatusDescription(
    // "Invalid JSON format.");

    // responseWrapper.setStatusDescriptions(statusDescription);

    // return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
    // }

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
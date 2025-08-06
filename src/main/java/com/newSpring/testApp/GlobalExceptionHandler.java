package com.newSpring.testApp;

import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.exception.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

import java.util.ConcurrentModificationException;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(value = AppException.class)
        public ResponseEntity<ResponseWrapper<?>> handleAppException(AppException e, WebRequest w) {
                HttpStatus status = HttpStatus.valueOf(200);
                StatusDescription response = StatusDescription.builder().statusCode(e.getErrorCode())
                                .statusDescription(e.getErrorMessage()).build();
                return ResponseEntity.status(status)
                                .body(ResponseWrapper.builder().statusDescriptions(response).build());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ResponseWrapper<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
                StringBuffer errorMessageMain = new StringBuffer();
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errorMessageMain.append(fieldName + " " + errorMessage + ", ");
                });

                System.out.println();

                StatusDescription statusDescription = StatusDescription.builder()
                                .statusCode(400)
                                .statusDescription(errorMessageMain.toString())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ResponseWrapper.builder().statusDescriptions(statusDescription).build());
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ResponseWrapper<?>> handleHttpMessageNotReadableException(
                        HttpMessageNotReadableException ex) {
                StatusDescription statusDescription = StatusDescription.builder()
                                .statusCode(400)
                                .statusDescription("Invalid JSON format.")
                                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ResponseWrapper.builder().statusDescriptions(statusDescription).build());
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ResponseWrapper<?>> handleAccessDeniedException(AccessDeniedException ex) {
                StatusDescription statusDescription = StatusDescription.builder()
                                .statusCode(403)
                                .statusDescription("Access Denied: You don't have permission to access this resource")
                                .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(ResponseWrapper.builder().statusDescriptions(statusDescription).build());
        }

        @ExceptionHandler(ConcurrentModificationException.class)
        public ResponseEntity<ResponseWrapper<?>> handleConcurrentModificationException(
                        ConcurrentModificationException ex) {
                StatusDescription statusDescription = StatusDescription.builder()
                                .statusCode(500)
                                .statusDescription(
                                                "Internal Server Error: JSON serialization failed due to circular references")
                                .build();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResponseWrapper.builder().statusDescriptions(statusDescription).build());
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ResponseWrapper<?>> handleAuthenticationException(AuthenticationException ex) {
                StatusDescription statusDescription = StatusDescription.builder()
                                .statusCode(401)
                                .statusDescription("Authentication failed: " + ex.getMessage())
                                .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(ResponseWrapper.builder().statusDescriptions(statusDescription).build());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ResponseWrapper<?>> handleGenericException(Exception ex) {
                StatusDescription statusDescription = StatusDescription.builder()
                                .statusCode(500)
                                .statusDescription("Internal Server Error: " + ex.getMessage())
                                .build();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResponseWrapper.builder().statusDescriptions(statusDescription).build());
        }
}
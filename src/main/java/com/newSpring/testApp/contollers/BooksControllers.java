package com.newSpring.testApp.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.RequestEntity.CreateBook;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.newSpring.testApp.service.BooksService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("books/")
@CrossOrigin(value = "*")
public class BooksControllers {
    @Autowired
    private BooksService booksService;

    @PostMapping("v1/addBook")
    public ResponseEntity<ResponseWrapper> addBook(@RequestBody CreateBook createBook) {
        ResponseWrapper responseWrapper1 = new ResponseWrapper();
        StatusDescription statusDescription1 = new StatusDescription();

        responseWrapper1.setStatusDescriptions(statusDescription1);

        try {
            responseWrapper1 = this.booksService.addBook(createBook);
            statusDescription1 = responseWrapper1.getStatusDescriptions();
            responseWrapper1.setStatusDescriptions(statusDescription1);
        } catch (Exception e) {
            statusDescription1.setStatusCode(500);
            statusDescription1.setStatusDescription("Internal Server Error");
            responseWrapper1.setStatusDescriptions(statusDescription1);
        } finally {
            return new ResponseEntity<>(responseWrapper1, HttpStatus.OK);
        }

    }

    @PostMapping("v1/addBookBulk")
    public ResponseEntity<ResponseWrapper> addBookBulk(@RequestBody List<CreateBook> createBooks) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {

            responseWrapper = this.booksService.addBookBulk(createBooks);
            statusDescription = responseWrapper.getStatusDescriptions();
            responseWrapper.setStatusDescriptions(statusDescription);
        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
            responseWrapper.setStatusDescriptions(statusDescription);
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @PostMapping("v1/addBookBulkCsv")
    public ResponseEntity<ResponseWrapper> addBookBulkCsv(@RequestParam("file") MultipartFile file) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {

            responseWrapper = this.booksService.addBookBulkCsv(file).get();
            statusDescription = responseWrapper.getStatusDescriptions();
            responseWrapper.setStatusDescriptions(statusDescription);
        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
            responseWrapper.setStatusDescriptions(statusDescription);
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @PostMapping("v1/filterBooks")
    public ResponseEntity<ResponseWrapper> filterBooks(String name, Long price, Long authorId, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {

            responseWrapper = this.booksService.filterBooks(name, price, authorId, createdAt, updatedAt).get();
            statusDescription = responseWrapper.getStatusDescriptions();
            responseWrapper.setStatusDescriptions(statusDescription);
        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
            responseWrapper.setStatusDescriptions(statusDescription);
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

}

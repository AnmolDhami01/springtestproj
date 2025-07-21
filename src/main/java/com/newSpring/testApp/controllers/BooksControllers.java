package com.newSpring.testApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.modal.BookModal;
import com.newSpring.testApp.modal.repo.BookRepo;
import com.newSpring.testApp.RequestEntity.CreateBook;
import com.newSpring.testApp.RequestEntity.FilterBooksRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.newSpring.testApp.service.BooksService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("books/")
@CrossOrigin(value = "*")
public class BooksControllers {
    @Autowired
    private BooksService booksService;

    @Autowired
    private BookRepo booksRepo;

    @GetMapping("v1/getBooks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper> getBooks() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        List<BookModal> books = this.booksRepo.findAll();

        responseWrapper.setBooks(books);
        statusDescription.setStatusCode(200);
        statusDescription.setStatusDescription("Books fetched successfully");

        responseWrapper.setStatusDescriptions(statusDescription);

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @PostMapping("v1/addBook")
    @PreAuthorize("hasRole('ADMIN')")
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
        }

        return new ResponseEntity<>(responseWrapper1, HttpStatus.OK);

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
    public ResponseEntity<ResponseWrapper> filterBooks(@RequestBody FilterBooksRequest filterBooksRequest,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {

            responseWrapper = this.booksService.filterBooks(filterBooksRequest);
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

    @GetMapping("v1/getBooksDto")
    public ResponseEntity<ResponseWrapper> getBooksDto() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {

            responseWrapper = this.booksService.getBooksDto();
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

    @PostMapping("v1/addBookFile")
    public ResponseEntity<ResponseWrapper> addBookFile(@RequestParam("file") MultipartFile file,
            @RequestParam("bookId") Long bookId) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {
            responseWrapper = this.booksService.addBookFile(file, bookId).get();
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

    @GetMapping("v1/generateCsvByUserId")
    public ResponseEntity<ResponseWrapper> generateCsvByUserId(@RequestParam("userId") Long userId) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        StatusDescription statusDescription = new StatusDescription();

        responseWrapper.setStatusDescriptions(statusDescription);

        try {
            responseWrapper = this.booksService.generateCsvByUserId(userId).get();
        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }
}

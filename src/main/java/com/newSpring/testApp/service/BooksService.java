package com.newSpring.testApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.newSpring.testApp.RequestEntity.CreateBook;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;

public interface BooksService {
    ResponseWrapper addBook(CreateBook createBook);

    ResponseWrapper addBookBulk(List<CreateBook> createBook);

    CompletableFuture<ResponseWrapper> addBookBulkCsv(MultipartFile file);

    ResponseWrapper filterBooks(String name, Long price, Long authorId, LocalDateTime createdAt,
            LocalDateTime updatedAt);
}

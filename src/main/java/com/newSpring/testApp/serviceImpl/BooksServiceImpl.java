package com.newSpring.testApp.serviceImpl;

import com.newSpring.testApp.RequestEntity.CreateBook;
import com.newSpring.testApp.ResponseEntinty.ResponseWrapper;
import com.newSpring.testApp.service.BooksService;
import com.newSpring.testApp.utils.Utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import com.newSpring.testApp.modal.repo.BookRepo;
import com.newSpring.testApp.ResponseEntinty.StatusDescription;
import com.newSpring.testApp.modal.BookModal;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.modal.repo.UsersRepo;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private BookRepo booksRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private Utils utils;

    private static final String BASE_UPLOAD_DIR = "src/main/resources/static/uploads";

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "jpg", "jpeg", "png");

    @Override
    public ResponseWrapper addBook(CreateBook createBook) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper(statusDescription, null);
        try {
            if (createBook.getUserId() == null || createBook.getBook() == null || createBook.getBook().getName() == null
                    || createBook.getBook().getPrice() == null) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("Invalid request");
                responseWrapper.setStatusDescriptions(statusDescription);
                return responseWrapper;
            }

            UserModal findUser = usersRepo.findById(createBook.getUserId());

            if (findUser == null) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("User not found");
                responseWrapper.setStatusDescriptions(statusDescription);
                return responseWrapper;
            }

            BookModal findBook = booksRepo.findByName(createBook.getBook().getName());

            if (findBook != null) {
                statusDescription.setStatusCode(409);
                statusDescription.setStatusDescription("Book already exists");
                responseWrapper.setStatusDescriptions(statusDescription);
                return responseWrapper;
            }

            createBook.getBook().setAuthor(findUser);

            booksRepo.save(createBook.getBook());
            statusDescription.setStatusCode(200);
            statusDescription.setStatusDescription("Book added successfully");
            responseWrapper.setStatusDescriptions(statusDescription);

        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error");
            responseWrapper.setStatusDescriptions(statusDescription);
        } finally {
            return responseWrapper;
        }
    }

    @Override
    public ResponseWrapper addBookBulk(List<CreateBook> createBooks) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper(statusDescription, null);

        try {

            if (createBooks == null || createBooks.isEmpty()) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("Request body cannot be empty");
                responseWrapper.setStatusDescriptions(statusDescription);
                return responseWrapper;
            }

            List<BookModal> validBooks = new ArrayList<BookModal>();
            List<String> errors = new ArrayList<String>();

            for (int i = 0; i < createBooks.size(); i++) {
                CreateBook bookRequest = createBooks.get(i);
                String errorPrefix = "Book " + (i + 1) + ": ";

                // Validate required fields
                if (bookRequest.getUserId() == null || bookRequest.getBook() == null ||
                        bookRequest.getBook().getName() == null || bookRequest.getBook().getPrice() == null) {
                    errors.add(errorPrefix + "Missing required fields (userId, book name, or price)");
                    continue;
                }

                // Check if user exists
                UserModal findUser = usersRepo.findById(bookRequest.getUserId());
                if (findUser == null) {
                    errors.add(errorPrefix + "User with ID " + bookRequest.getUserId() + " not found");
                    continue;
                }

                // Check if book already exists
                BookModal findBook = booksRepo.findByName(bookRequest.getBook().getName());
                if (findBook != null) {
                    errors.add(errorPrefix + "Book with name '" + bookRequest.getBook().getName() + "' already exists");
                    continue;
                }

                // Set the author and add to valid books list
                bookRequest.getBook().setAuthor(findUser);
                validBooks.add(bookRequest.getBook());
            }

            // Save all valid books
            if (!validBooks.isEmpty()) {
                booksRepo.saveAll(validBooks);
            }

            // Set response based on results
            if (errors.isEmpty()) {
                statusDescription.setStatusCode(200);
                statusDescription.setStatusDescription("All books added successfully");
            } else if (validBooks.isEmpty()) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("No books were added. Errors: " + String.join("; ", errors));
            } else {
                statusDescription.setStatusCode(207); // Multi-status
                statusDescription
                        .setStatusDescription("Some books added successfully. Errors: " + String.join("; ", errors));
            }

            responseWrapper.setStatusDescriptions(statusDescription);

        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
            responseWrapper.setStatusDescriptions(statusDescription);
        }

        return responseWrapper;
    }

    @Override
    public CompletableFuture<ResponseWrapper> addBookBulkCsv(MultipartFile file) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper(statusDescription, null);

        try {
            if (file == null || file.isEmpty()) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("Please select a file to upload");
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.endsWith(".csv")) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("Invalid file format. Please upload a CSV file");
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            statusDescription.setStatusCode(200);
            statusDescription.setStatusDescription("File uploaded successfully");
            responseWrapper.setStatusDescriptions(statusDescription);

            utils.addBookBulkCsv(file);

            return CompletableFuture.completedFuture(responseWrapper);

        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
            responseWrapper.setStatusDescriptions(statusDescription);
        } finally {
            return CompletableFuture.completedFuture(responseWrapper);
        }
    }

    @Override
    public CompletableFuture<ResponseWrapper> addBookFile(MultipartFile file, Long bookId) {
        StatusDescription statusDescription = new StatusDescription();
        ResponseWrapper responseWrapper = new ResponseWrapper(statusDescription, null);

        try {
            if (file == null || file.isEmpty() || bookId == null || bookId == 0) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("Bad Request");
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            // Check file name and extension
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("File name is missing.");
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            String extension = originalFilename.split("\\.")[originalFilename.split("\\.").length - 1].toLowerCase();

            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                statusDescription.setStatusCode(400);
                statusDescription
                        .setStatusDescription("Invalid file type. Allowed: " + String.join(", ", ALLOWED_EXTENSIONS));
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            long maxSizeInBytes = 5 * 1024 * 1024;
            if (file.getSize() > maxSizeInBytes) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("File is too large. Max size: 5 MB.");
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            BookModal book = booksRepo.findById(bookId).orElse(null);
            if (book == null) {
                statusDescription.setStatusCode(400);
                statusDescription.setStatusDescription("Book not found");
                responseWrapper.setStatusDescriptions(statusDescription);
                return CompletableFuture.completedFuture(responseWrapper);
            }

            Path bookFolder = Paths.get(BASE_UPLOAD_DIR, bookId.toString());
            Files.createDirectories(bookFolder);

            String fileName = (book.getName() + "_" + new java.util.Date().toString()
                    + "." + extension).replace(" ", "_").replace(":", "-");

            Path filePath = bookFolder.resolve(fileName);

            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

            book.setFileName(fileName);
            book.setFilePath(filePath.toString());
            booksRepo.save(book);

            statusDescription.setStatusCode(200);
            statusDescription.setStatusDescription("File uploaded successfully to: " + filePath.toString());
            responseWrapper.setStatusDescriptions(statusDescription);
            return CompletableFuture.completedFuture(responseWrapper);

        } catch (Exception e) {
            e.printStackTrace();
            statusDescription.setStatusCode(500);
            statusDescription.setStatusDescription("Internal Server Error: " + e.getMessage());
            responseWrapper.setStatusDescriptions(statusDescription);
        } finally {
            return CompletableFuture.completedFuture(responseWrapper);
        }
    }

    @Override
    public byte[] getBookFile(Long bookId) {
        try {
            BookModal book = booksRepo.findById(bookId).orElse(null);
            if (book == null || book.getFilePath() == null) {
                throw new RuntimeException("Book not found or no file attached");
            }

            Path filePath = Paths.get(book.getFilePath());
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found on disk");
            }

            return Files.readAllBytes(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }
}

package com.newSpring.testApp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.newSpring.testApp.modal.BookModal;
import com.newSpring.testApp.modal.BulkErrorModal;
import com.newSpring.testApp.modal.UserModal;
import com.newSpring.testApp.modal.repo.BookRepo;
import com.newSpring.testApp.modal.repo.BulkErrorRepo;
import com.newSpring.testApp.modal.repo.UsersRepo;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class Utils {

    @Autowired
    private BookRepo booksRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private BulkErrorRepo bulkErrorRepo;

    @Async("taskExecutor")
    public void addBookBulkCsv(MultipartFile file) {

        List<BookModal> books = new ArrayList<BookModal>();
        Integer errorCount = 0;
        Integer successCount = 0;
        Integer lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip header row
                if (lineNumber == 1) {
                    continue;
                }

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                // Check if we have enough columns (book_name, price, author_name)
                if (data.length < 3) {
                    errorCount++;
                    continue;
                }

                String bookName = data[0].trim();
                String priceStr = data[1].trim();
                String authorName = data[2].trim();

                // Validate required fields
                if (bookName.isEmpty() || priceStr.isEmpty() || authorName.isEmpty()) {
                    errorCount++;
                    continue;
                }

                // Check if book already exists
                BookModal findBook = this.booksRepo.findByName(bookName);
                if (findBook != null) {
                    continue;
                }

                // Parse price
                Long price;
                try {
                    price = Long.parseLong(priceStr);
                } catch (NumberFormatException e) {
                    errorCount++;
                    continue;
                }

                // Find author by name
                UserModal author = usersRepo.findByName(authorName);
                if (author == null) {
                    errorCount++;
                    continue;
                }

                // Create and add book
                successCount++;
                BookModal book = new BookModal();
                book.setName(bookName);
                book.setPrice(price);
                book.setAuthor(author);
                books.add(book);
            }

            // Save all valid books
            if (!books.isEmpty()) {
                this.booksRepo.saveAll(books);
            }

            BulkErrorModal bulkError = new BulkErrorModal();
            bulkError.setFileCount(lineNumber - 1);
            bulkError.setFileCode(file.getOriginalFilename() + new Date().toString());
            bulkError.setErrorCount(errorCount);
            bulkError.setSuccessCount(successCount);
            bulkError.setType("book");
            this.bulkErrorRepo.save(bulkError);

            // Set response based on results
            // if (errors.isEmpty()) {
            // statusDescription.setStatusCode(200);
            // statusDescription.setStatusDescription("All books added successfully");
            // } else if (books.isEmpty()) {
            // statusDescription.setStatusCode(400);
            // statusDescription.setStatusDescription("No books were added. Errors: " +
            // String.join("; ", errors));
            // } else {
            // statusDescription.setStatusCode(207);
            // statusDescription.setStatusDescription(
            // "Some books added successfully. Errors: " + String.join("; ", errors));
            // }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}

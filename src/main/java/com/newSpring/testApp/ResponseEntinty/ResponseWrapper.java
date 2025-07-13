package com.newSpring.testApp.ResponseEntinty;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newSpring.testApp.modal.BookModal;
import com.newSpring.testApp.modal.UserModal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper implements Serializable {

    StatusDescription statusDescriptions;

    List<UserModal> users;

    List<BookModal> books;

    Page<BookModal> booksPage;

    public ResponseWrapper() {
    }

    public ResponseWrapper(StatusDescription statusDescriptions, List<UserModal> users) {
        this.statusDescriptions = statusDescriptions;
        this.users = users;
    }

    public void setStatusDescriptions(StatusDescription statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
    }

    public void setUsers(List<UserModal> users) {
        this.users = users;
    }

    public StatusDescription getStatusDescriptions() {
        return statusDescriptions;
    }

    public List<UserModal> getUsers() {
        return users;
    }

    public void setBooks(List<BookModal> books) {
        this.books = books;
    }

    public List<BookModal> getBooks() {
        return books;
    }

    public void setBooksPage(Page<BookModal> booksPage) {
        this.booksPage = booksPage;
    }

    public Page<BookModal> getBooksPage() {
        return booksPage;
    }

    @Override
    public String toString() {
        return "ResponseWrapper [statusDescriptions=" + statusDescriptions + ", users=" + users + ", books=" + books
                + "]";
    }
}

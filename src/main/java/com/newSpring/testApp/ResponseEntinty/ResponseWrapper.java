package com.newSpring.testApp.ResponseEntinty;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newSpring.testApp.modal.BookModal;
import com.newSpring.testApp.modal.UserModal;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper implements Serializable {

    StatusDescription statusDescriptions;

    List<UserModal> users;

    UserModal user;

    List<BookModal> books;

    Page<BookModal> booksPage;

    @Override
    public String toString() {
        return "ResponseWrapper [statusDescriptions=" + statusDescriptions + ", users=" + users + ", books=" + books
                + "]";
    }
}

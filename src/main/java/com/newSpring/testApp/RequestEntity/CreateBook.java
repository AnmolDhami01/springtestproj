package com.newSpring.testApp.RequestEntity;

import com.newSpring.testApp.modal.BookModal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBook {
    private Long userId;
    private BookModal book;
}

package com.newSpring.bookservice.RequestEntity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.util.List;

import com.newSpring.bookservice.modal.BookModal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBook {
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "price is required")
    private Long price;
    private List<String> tags;
}

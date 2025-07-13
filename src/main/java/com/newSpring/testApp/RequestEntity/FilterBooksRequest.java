package com.newSpring.testApp.RequestEntity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.newSpring.testApp.utils.LocalDateTimeDeserializer;

import lombok.Data;

@Data
public class FilterBooksRequest {
    private String name;
    private Long price;
    private Long authorId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    private Integer pageNo;
    private Integer pageSize;

    // sample json
    // {
    // "name": "Book 1",
    // "price": 100,
    // "authorId": 1,
    // "createdAt": "2025-07-09",
    // "updatedAt": "2025-07-09T14:30:00",
    // "pageNo": 0,
    // "pageSize": 10
    // }

}

package com.newSpring.bookservice.RequestEntity;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String password;
}

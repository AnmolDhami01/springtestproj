package com.newSpring.testApp.RequestEntity;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String password;
}

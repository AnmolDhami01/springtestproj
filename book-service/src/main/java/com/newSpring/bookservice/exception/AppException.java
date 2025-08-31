package com.newSpring.bookservice.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 8799518488180428275L;

    private int errorCode;
    private String errorMessage;

    public AppException(int errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}

package com.app.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String exceptionMessage;
    private LocalDateTime localDateTime;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.localDateTime = LocalDateTime.now();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}

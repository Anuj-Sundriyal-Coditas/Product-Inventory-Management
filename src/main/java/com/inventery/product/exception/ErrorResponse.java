package com.inventery.product.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String message;

    public ErrorResponse(LocalDateTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
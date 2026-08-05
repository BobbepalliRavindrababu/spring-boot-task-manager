package com.ravindra.taskmanager.exception;

import java.time.LocalDateTime;
public class ErrorResponse {

    private LocalDateTime timestramp;
    private int status;
    private String message;

    public ErrorResponse(LocalDateTime timestramp, int status, String message) {
        this.timestramp = timestramp;
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestramp() {
        return timestramp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

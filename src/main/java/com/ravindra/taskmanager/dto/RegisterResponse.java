package com.ravindra.taskmanager.dto;

public class RegisterResponse {
    public String userName;
    public String email;
    public String message;

    public RegisterResponse(String userName, String email, String message) {
        this.userName = userName;
        this.email = email;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

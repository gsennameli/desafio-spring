package com.example.desafioSpring.dtos;

public class ErrorHandlingDTO {
    private String message;

    public ErrorHandlingDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

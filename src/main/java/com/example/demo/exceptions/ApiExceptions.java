package com.example.demo.exceptions;

public class ApiExceptions extends RuntimeException{

    public ApiExceptions(String message) {
        super(message);
    }

    public ApiExceptions() {
    }
}

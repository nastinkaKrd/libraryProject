package com.library.project.exceptions;

public class ApiRequestExceptionAlreadyReported extends RuntimeException{

    public ApiRequestExceptionAlreadyReported(String message) {
        super(message);
    }
}

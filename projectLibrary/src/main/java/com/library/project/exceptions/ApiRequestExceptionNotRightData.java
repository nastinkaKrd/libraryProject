package com.library.project.exceptions;

public class ApiRequestExceptionNotRightData extends RuntimeException{
    public ApiRequestExceptionNotRightData(String message) {
        super(message);
    }
}

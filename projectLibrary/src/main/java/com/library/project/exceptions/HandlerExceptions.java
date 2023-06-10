package com.library.project.exceptions;

import com.library.project.controllers.AuthenticationController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice(basePackages = "com.libraryProject.project.controllers",
        basePackageClasses = AuthenticationController.class)
public class HandlerExceptions {
    @ExceptionHandler(value = {ApiRequestExceptionNotFound.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse handleApiRequestException(@NotNull HttpServletRequest request, ApiRequestExceptionNotFound exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {ApiRequestExceptionAlreadyReported.class})
    @ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
    public ErrorResponse handleApiRequestExceptionAlreadyReported(@NotNull HttpServletRequest request, ApiRequestExceptionAlreadyReported exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {ApiBadCredentialsException.class})
    @ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
    public ErrorResponse handleApiBadCredentialsException(@NotNull HttpServletRequest request, ApiRequestExceptionAlreadyReported exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {ApiRequestExceptionNotRightData.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleApiRequestExceptionNotRightData(@NotNull HttpServletRequest request, ApiRequestExceptionNotRightData exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {ApiRequestExceptionChangeStatus.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleApiRequestExceptionChangeStatus(@NotNull HttpServletRequest request, ApiRequestExceptionChangeStatus exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }


    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(@NotNull HttpServletRequest request, MethodArgumentTypeMismatchException exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(@NotNull HttpServletRequest request, IllegalArgumentException exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse MethodArgumentNotValidException(@NotNull HttpServletRequest request, MethodArgumentNotValidException exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(@NotNull HttpServletRequest request, Exception exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }
}

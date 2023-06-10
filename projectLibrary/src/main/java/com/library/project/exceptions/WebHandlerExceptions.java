package com.library.project.exceptions;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(basePackages = "com.libraryProject.project.webControllers")
public class WebHandlerExceptions {


    @ExceptionHandler(value = {ApiRequestExceptionNotFound.class})
    public String handleApiRequestException(ApiRequestExceptionNotFound exception,
                                            RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {ApiRequestExceptionAlreadyReported.class})
    public String handleApiRequestExceptionAlreadyReported(ApiRequestExceptionAlreadyReported exception,
                                                           RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {ApiRequestExceptionNotRightData.class})
    public String handleApiRequestExceptionNotRightData(ApiRequestExceptionNotRightData exception,
                                                        RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {ApiRequestExceptionChangeStatus.class})
    public String handleApiRequestExceptionChangeStatus(ApiRequestExceptionChangeStatus exception,
                                                        RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                            RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public String handleIllegalArgumentException(IllegalArgumentException exception,
                                                 RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public String MethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                  RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }

    @ExceptionHandler(value = {Exception.class})
    public String handleException(Exception exception,
                                  RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error_message", exception.getMessage());
        return "redirect:/errorPage";
    }
}

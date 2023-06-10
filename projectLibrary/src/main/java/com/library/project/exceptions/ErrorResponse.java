package com.library.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ErrorResponse {
    private String path;
    String message;
}

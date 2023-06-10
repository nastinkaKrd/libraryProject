package com.library.project.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class ApiBadCredentialsException extends BadCredentialsException {
    public ApiBadCredentialsException() {
        super("Provided credentials are incorrect");
    }
}

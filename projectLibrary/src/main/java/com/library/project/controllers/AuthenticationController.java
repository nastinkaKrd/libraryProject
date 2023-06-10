package com.library.project.controllers;

import com.library.project.services.AuthenticationService;
import com.library.project.dto.AuthenticationRequest;
import com.library.project.dto.RegisterRequest;
import com.library.project.dto.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> createJwtToken(@RequestBody AuthenticationRequest authRequest)  {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}

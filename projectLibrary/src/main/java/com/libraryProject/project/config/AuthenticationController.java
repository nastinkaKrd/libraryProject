package com.libraryProject.project.config;

import com.libraryProject.project.dto.AuthResponseDto;
import com.libraryProject.project.dto.AuthenticationRequest;
import com.libraryProject.project.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequest registerRequest) throws RuntimeException {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> createJwtToken(@RequestBody AuthenticationRequest authRequest) throws RuntimeException {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}

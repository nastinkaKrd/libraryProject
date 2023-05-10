package com.libraryProject.project.controllers;

import com.libraryProject.project.dto.AuthResponseDto;
import com.libraryProject.project.dto.AuthenticationRequest;
import com.libraryProject.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthenticationController {
    @Autowired
    @Qualifier("userServiceImplements")
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    public AuthResponseDto createJwtToken(@RequestBody AuthenticationRequest authRequest) throws RuntimeException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect password or username!", e);
        }

        final UserDetails user = userDetailsService.loadUserByUsername(authRequest.getUsername());

        String jwt = jwtUtil.generateToken(user);

        return new AuthResponseDto(jwt);

    }
}

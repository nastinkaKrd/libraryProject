package com.libraryProject.project.config;

import com.libraryProject.project.dto.AuthResponseDto;
import com.libraryProject.project.dto.AuthenticationRequest;
import com.libraryProject.project.dto.RegisterRequest;
import com.libraryProject.project.models.Token;
import com.libraryProject.project.models.User;
import com.libraryProject.project.models.UserRoles;
import com.libraryProject.project.repositories.TokenRepository;
import com.libraryProject.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    public AuthResponseDto register(RegisterRequest registerRequest) {
        List<User> users = userRepository.findAll();
        int id = users.get(users.size()-1).getUserId() + 1;
        var user = User.builder()
                .userId(id)
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .active(true)
                .role(UserRoles.ROLE_USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthResponseDto.builder()
                .jwt(jwtToken).build();
    }

    public AuthResponseDto authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthResponseDto.builder()
                .jwt(jwtToken).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        List<Token> tokens = tokenRepository.findAll();
        int id = tokens.get(tokens.size()-1).getTokenId()+1 ;
        var token = Token
                .builder()
                .tokenId(id)
                .value(jwtToken)
                .user(user)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(User user) {
        var tokens = tokenRepository.findByUser(user);
        List<Token> validUserTokens = new ArrayList<>();
        for(Token token: tokens){
            if (!token.expired && !token.revoked){
                validUserTokens.add(token);
            }
        }
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

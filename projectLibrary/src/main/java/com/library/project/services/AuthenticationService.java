package com.library.project.services;

import com.library.project.dto.*;
import com.library.project.mappers.BuilderToDto;
import com.library.project.mappers.BuilderToModel;
import com.library.project.models.UserRoles;
import com.library.project.models.User;
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
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequest registerRequest) {
        List<UserDto> users = userService.getAllUsers();
        int id = users.get(users.size()-1).getUserId() + 1;
        var user = UserDto.builder()
                .userId(id)
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .active(true)
                .role(UserRoles.ROLE_USER)
                .build();
        userService.saveUser(user);
        var jwtToken = jwtService.generateToken(BuilderToModel.toModel(user));
        saveUserToken(BuilderToModel.toModel(user), jwtToken);
        return AuthResponseDto.builder()
                .jwt(jwtToken).build();
    }

    public AuthResponseDto authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));
        var user = userService.getUserByEmail(authenticationRequest.getEmail());
        var jwtToken = jwtService.generateToken(BuilderToModel.toModel(user));
        revokeAllUserTokens(BuilderToModel.toModel(user));
        saveUserToken(BuilderToModel.toModel(user), jwtToken);
        return AuthResponseDto.builder()
                .jwt(jwtToken).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        List<TokenDto> tokens = tokenService.getAllTokens();
        int id = tokens.get(tokens.size()-1).getTokenId()+1 ;
        var token = TokenDto
                .builder()
                .tokenId(id)
                .value(jwtToken)
                .user(user)
                .build();
        tokenService.saveToken(token);
    }


    private void revokeAllUserTokens(User user) {
        var tokens = tokenService.getTokenByUser(BuilderToDto.toDto(user));
        List<TokenDto> validUserTokens = new ArrayList<>();
        for(TokenDto token: tokens){
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
        tokenService.updateTokens(validUserTokens);
    }
}

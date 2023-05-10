package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.UserDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotFound;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotRightData;
import com.libraryProject.project.models.User;
import com.libraryProject.project.models.UserRoles;
import com.libraryProject.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplements implements UserService, UserDetailsService {
    UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDto logIn(String email, String password, int id) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            if (!email.equals(data.get().getEmail())){
                throw new ApiRequestExceptionNotRightData("Not right email");
            }
            else if (!password.equals(data.get().getPassword())){
                throw new ApiRequestExceptionNotRightData("Not right password");
            }else if (data.get().isActive()){
                throw new ApiRequestExceptionAlreadyReported("User already is logged");
            }
            else {
                User user = userRepository.findById(id).get();
                user.setActive(true);
                userRepository.save(user);
            }
            return BuilderToDto.toDto(userRepository.findById(id).get());
        }else {
            throw new ApiRequestExceptionNotFound("User is not found");
        }
    }

    @Override
    public UserDto logOut(String email, String password, int id) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            if (!email.equals(data.get().getEmail())){
                throw new ApiRequestExceptionNotRightData("Not right email");
            }
            else if (!password.equals(data.get().getPassword())){
                throw new ApiRequestExceptionNotRightData("Not right password");
            }else if (!data.get().isActive()){
                throw new ApiRequestExceptionAlreadyReported("User already is unlogged");
            }
            else {
                User user = userRepository.findById(id).get();
                user.setActive(false);
                userRepository.save(user);
            }
            return BuilderToDto.toDto(userRepository.findById(id).get());
        }else {
            throw new ApiRequestExceptionNotFound("User is not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.builder()
                .active(true)
                .userId(1)
                .role(UserRoles.ROLE_LIBRARIAN)
                .userName("user")
                .email("user@gmail.com")
                .password(passwordEncoder.encode("userPasww"))
                .build();
    }
}

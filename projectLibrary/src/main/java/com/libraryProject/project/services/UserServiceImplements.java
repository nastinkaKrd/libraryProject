package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.UserDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotFound;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotRightData;
import com.libraryProject.project.models.User;
import com.libraryProject.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplements implements UserService{
    UserRepository userRepository;

    @Override
    public UserDto logIn(String email, String password, int id) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            if (!email.equals(data.get().getEmail())){
                throw new ApiRequestExceptionNotRightData("Not right email");
            }
            else if (!password.equals(data.get().getPassword())){
                throw new ApiRequestExceptionNotRightData("Not right password");
            }else if (data.get().isLogged()){
                throw new ApiRequestExceptionAlreadyReported("User already is logged");
            }
            else {
                userRepository.save(new User(data.get().getUserId(), data.get().getUserName(), email, password, true));
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
            }else if (!data.get().isLogged()){
                throw new ApiRequestExceptionAlreadyReported("User already is unlogged");
            }
            else {
                userRepository.save(new User(data.get().getUserId(), data.get().getUserName(), email, password, false));
            }
            return BuilderToDto.toDto(userRepository.findById(id).get());
        }else {
            throw new ApiRequestExceptionNotFound("User is not found");
        }
    }
}

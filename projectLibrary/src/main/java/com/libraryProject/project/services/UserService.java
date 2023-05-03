package com.libraryProject.project.services;

import com.libraryProject.project.dto.UserDto;

public interface UserService {
    UserDto logIn(String email, String password, int id);

    UserDto logOut(String email, String password, int id);
}

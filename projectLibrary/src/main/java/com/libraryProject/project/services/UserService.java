package com.libraryProject.project.services;

import com.libraryProject.project.dto.CreateUserForm;
import com.libraryProject.project.dto.UserDto;

public interface UserService {
    UserDto logIn(String email, String password, int id);

    UserDto logOut(String email, String password, int id);

    boolean create(CreateUserForm createUserForm);

    boolean createAdminIfNotExists();
}

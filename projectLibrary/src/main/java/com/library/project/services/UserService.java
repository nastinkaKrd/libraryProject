package com.library.project.services;

import com.library.project.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserByEmail(String email);
    void saveUser(UserDto userDto);
}

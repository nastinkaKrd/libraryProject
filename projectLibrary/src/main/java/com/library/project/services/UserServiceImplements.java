package com.library.project.services;
import com.library.project.dto.UserDto;
import com.library.project.mappers.BuilderToDto;
import com.library.project.mappers.BuilderToModel;
import com.library.project.models.User;
import com.library.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImplements implements UserService{
    UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(BuilderToDto.toDto(user)));
        return userDtos;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return BuilderToDto.toDto(user);
    }

    @Override
    public void saveUser(UserDto userDto) {
        userRepository.save(BuilderToModel.toModel(userDto));
    }
}

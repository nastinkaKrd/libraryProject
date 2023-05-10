package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.CreateUserForm;
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
    private final UserRepository userRepository;
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
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public boolean create(CreateUserForm createUserForm) {

        if (userRepository.existsByUsername(createUserForm.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        User user = User.builder()
                .username(createUserForm.getUsername())
                .password(passwordEncoder.encode(createUserForm.getPassword()))
                .role(UserRoles.ROLE_USER)
                .active(true)
                .build();

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean createAdminIfNotExists() {
        if (!userRepository.existsByUsername("admin")) {
            User user = User.builder()
                    .userId(12345)
                    .username("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(UserRoles.ROLE_ADMIN)
                    .active(true)
                    .build();

            userRepository.save(user);
            return true;
        }
        return false;
    }
}

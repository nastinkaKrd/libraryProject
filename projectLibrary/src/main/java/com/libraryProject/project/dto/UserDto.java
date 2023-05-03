package com.libraryProject.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank
    private int userId;
    @NotBlank
    private String userName;
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private boolean isLogged;
}

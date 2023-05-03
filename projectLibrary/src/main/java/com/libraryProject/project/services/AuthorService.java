package com.libraryProject.project.services;

import com.libraryProject.project.dto.AuthorDto;
import com.libraryProject.project.models.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> showAuthors();

}

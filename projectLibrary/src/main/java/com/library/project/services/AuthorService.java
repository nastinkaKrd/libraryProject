package com.library.project.services;

import com.library.project.dto.AuthorDto;
import com.library.project.models.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDto> showAuthors();
    Optional<Author> getById(int id);
    void saveAuthor(AuthorDto authorDto);
}

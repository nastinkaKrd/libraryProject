package com.library.project.services;

import com.library.project.dto.AuthorDto;
import com.library.project.models.Author;
import com.library.project.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImplements authorService;

    @BeforeEach
    void setUp(){
        authorService = new AuthorServiceImplements(authorRepository);
    }

    @Test
    void canShowAuthors() {
        List<Author> authors = new ArrayList<>(List.of(
                new Author(1, "name 1"),
                new Author(2, "name 2"),
                new Author(3, "name 3")));
        List<AuthorDto> dtos = new ArrayList<>(List.of(
                new AuthorDto(1, "name 1"),
                new AuthorDto(2, "name 2"),
                new AuthorDto(3, "name 3")));
        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDto> authorDtos = authorService.showAuthors();
        assertEquals(authorDtos, dtos);
        verify(authorRepository).findAll();
    }
}
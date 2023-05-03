package com.libraryProject.project.services;

import com.libraryProject.project.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private AuthorServiceImplements authorService;

    @BeforeEach
    void setUp(){
        authorService = new AuthorServiceImplements(authorRepository);
    }

    @Test
    void canShowAuthors() {
        authorService.showAuthors();
        verify(authorRepository).findAll();
    }
}
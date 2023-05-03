package com.libraryProject.project.services;

import com.libraryProject.project.repositories.PublishCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PublishCompanyServiceImplementsTest {

    @Mock
    private PublishCompanyRepository publishCompanyRepository;
    private PublishCompanyServiceImplements publishCompanyService;
    @BeforeEach
    void setUp(){
        publishCompanyService = new PublishCompanyServiceImplements(publishCompanyRepository);
    }

    @Test
    void canShowCompanies() {
        publishCompanyService.getCompanies();
        verify(publishCompanyRepository).findAll();
    }
}
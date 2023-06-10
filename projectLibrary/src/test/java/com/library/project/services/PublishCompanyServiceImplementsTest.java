package com.library.project.services;
import com.library.project.dto.PublishCompanyDto;
import com.library.project.models.PublishCompany;
import com.library.project.repositories.PublishCompanyRepository;
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
class PublishCompanyServiceImplementsTest {

    @Mock
    private PublishCompanyRepository publishCompanyRepository;
    @InjectMocks
    private PublishCompanyServiceImplements publishCompanyService;
    @BeforeEach
    void setUp(){
        publishCompanyService = new PublishCompanyServiceImplements(publishCompanyRepository);
    }

    @Test
    void canShowCompanies() {
        List<PublishCompany> companies = new ArrayList<>(List.of(
                new PublishCompany(1, "name 1"),
                new PublishCompany(2, "name 2"),
                new PublishCompany(3, "name 3")));
        List<PublishCompanyDto> dtos = new ArrayList<>(List.of(
                new PublishCompanyDto(1, "name 1"),
                new PublishCompanyDto(2, "name 2"),
                new PublishCompanyDto(3, "name 3")));
        when(publishCompanyRepository.findAll()).thenReturn(companies);
        List<PublishCompanyDto> companyDtos = publishCompanyService.getCompanies();
        assertEquals(companyDtos, dtos);
        verify(publishCompanyRepository).findAll();
    }
}
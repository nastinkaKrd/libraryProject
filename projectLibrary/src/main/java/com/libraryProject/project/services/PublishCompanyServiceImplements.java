package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.PublishCompanyDto;
import com.libraryProject.project.models.PublishCompany;
import com.libraryProject.project.repositories.PublishCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublishCompanyServiceImplements implements PublishCompanyService{
    PublishCompanyRepository publishCompanyRepository;
    @Override
    public List<PublishCompanyDto> getCompanies(){
        List<PublishCompany> listOfCompanies = publishCompanyRepository.findAll();
        List<PublishCompanyDto> companyDtos = new ArrayList<>();
        for (PublishCompany temp: listOfCompanies){
            companyDtos.add(BuilderToDto.toDto(temp));
        }
        return companyDtos;

    }
}

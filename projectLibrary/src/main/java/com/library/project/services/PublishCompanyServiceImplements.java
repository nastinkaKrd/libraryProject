package com.library.project.services;

import com.library.project.mappers.BuilderToDto;
import com.library.project.repositories.PublishCompanyRepository;
import com.library.project.dto.PublishCompanyDto;
import com.library.project.mappers.BuilderToModel;
import com.library.project.models.PublishCompany;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<PublishCompany> getById(int id) {
        return publishCompanyRepository.findById(id);
    }

    @Override
    public void savePublishCompany(PublishCompanyDto publishCompany) {
        publishCompanyRepository.save(BuilderToModel.toModel(publishCompany));
    }
}

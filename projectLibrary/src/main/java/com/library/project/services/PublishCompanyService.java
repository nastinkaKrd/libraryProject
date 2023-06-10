package com.library.project.services;

import com.library.project.dto.PublishCompanyDto;
import com.library.project.models.PublishCompany;
import java.util.List;
import java.util.Optional;

public interface PublishCompanyService {
    List<PublishCompanyDto> getCompanies();
    Optional<PublishCompany> getById(int id);
    void savePublishCompany(PublishCompanyDto publishCompany);
}

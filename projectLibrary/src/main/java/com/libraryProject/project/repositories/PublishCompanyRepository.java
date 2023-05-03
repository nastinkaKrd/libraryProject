package com.libraryProject.project.repositories;

import com.libraryProject.project.models.PublishCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishCompanyRepository extends JpaRepository<PublishCompany, Integer> {
}

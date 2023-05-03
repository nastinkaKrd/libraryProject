package com.libraryProject.project.repositories;

import com.libraryProject.project.models.PrintedElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintedElementRepository extends JpaRepository<PrintedElement, Integer> {

}

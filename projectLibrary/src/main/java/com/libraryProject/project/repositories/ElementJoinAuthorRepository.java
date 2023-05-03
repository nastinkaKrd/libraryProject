package com.libraryProject.project.repositories;

import com.libraryProject.project.models.ElementJoinAuthor;
import com.libraryProject.project.models.PrintedElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface ElementJoinAuthorRepository extends JpaRepository<ElementJoinAuthor, Integer> {
    List<ElementJoinAuthor> findAllByPrintedElementId(PrintedElement printedElement);
}

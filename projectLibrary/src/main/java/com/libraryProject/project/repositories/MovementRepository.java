package com.libraryProject.project.repositories;

import com.libraryProject.project.models.Movement;
import com.libraryProject.project.models.PrintedElement;
import com.libraryProject.project.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {
    List<Movement> findAllByReaderId(Reader reader);
    List<Movement> findAllByPrintedElementId(PrintedElement printedElement);
}

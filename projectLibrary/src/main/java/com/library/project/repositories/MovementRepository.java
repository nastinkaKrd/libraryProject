package com.library.project.repositories;

import com.library.project.models.Movement;
import com.library.project.models.PrintedElement;
import com.library.project.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {
    List<Movement> findAllByReader(Reader reader);
    List<Movement> findAllByPrintedElement(PrintedElement printedElement);
}

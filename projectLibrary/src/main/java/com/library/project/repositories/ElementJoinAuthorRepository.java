package com.library.project.repositories;

import com.library.project.models.ElementJoinAuthor;
import com.library.project.models.PrintedElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface ElementJoinAuthorRepository extends JpaRepository<ElementJoinAuthor, Integer> {
    List<ElementJoinAuthor> findAllByPrintedElementId(PrintedElement printedElement);
}

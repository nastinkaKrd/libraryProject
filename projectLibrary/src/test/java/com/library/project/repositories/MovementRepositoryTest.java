package com.library.project.repositories;

import com.library.project.models.Movement;
import com.library.project.models.PrintedElement;
import com.library.project.models.PublishCompany;
import com.library.project.models.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;


@DataJpaTest
class MovementRepositoryTest {

    @Autowired
    private MovementRepository underTestMovement;
    @Autowired
    private ReaderRepository underTestReader;
    @Autowired
    private PrintedElementRepository underTestElement;
    @Autowired
    private PublishCompanyRepository publishCompanyRepository;
    @Test
    void itShouldFindAllInformationByReaderId() {
        Date date = new Date();
        java.sql.Date sqlDdate = new java.sql.Date(date.getTime());
        Reader reader = new Reader(942109999, "Janson Dakson");
        PrintedElement element = new PrintedElement(40, "some book3", "book", "Fantasy",
                14, 2021, 2, new PublishCompany(1, "Ballantine Books"));
        Movement movement = new Movement(489489999, "taked 2", sqlDdate, reader, element);
        publishCompanyRepository.save(new PublishCompany(1, "Ballantine Books"));
        underTestReader.save(reader);
        underTestElement.save(element);
        underTestMovement.save(movement);
        Movement movementFirst = underTestMovement.findAllByReader(reader).get(0);
        assert(movementFirst).equals(movement);
    }

    @Test
    void itShouldFindAllByPrintedElementId() {
        Date date = new Date();
        java.sql.Date sqlDdate = new java.sql.Date(date.getTime());
        Reader reader = new Reader(942109999, "Janson Dakson");
        PrintedElement element = new PrintedElement(40, "some book3", "book", "Fantasy",
                14, 2021, 2, new PublishCompany(1, "Ballantine Books"));
        Movement movement = new Movement(489489999, "taked 2", sqlDdate, reader, element);
        publishCompanyRepository.save(new PublishCompany(1, "Ballantine Books"));
        underTestReader.save(reader);
        underTestElement.save(element);
        underTestMovement.save(movement);
        Movement movementFirst = underTestMovement.findAllByPrintedElement(element).get(0);
        assert(movementFirst).equals(movement);
    }

}
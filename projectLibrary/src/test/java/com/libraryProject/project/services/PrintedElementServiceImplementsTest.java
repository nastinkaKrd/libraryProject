package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.AllDataOfElementDto;
import com.libraryProject.project.dto.AuthorDto;
import com.libraryProject.project.models.*;
import com.libraryProject.project.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrintedElementServiceImplementsTest {
    @Mock
    private PrintedElementRepository printedElementRepository;
    @Mock
    private ReaderRepository readerRepository;
    @Mock
    private MovementRepository movementRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ElementJoinAuthorRepository elementJoinAuthorRepository;
    @Mock
    private PrintedElementService printedElementServiceImplements;
    @Mock
    private PublishCompanyRepository publishCompanyRepository;
    @BeforeEach
    void setUp() {
        printedElementServiceImplements = new PrintedElementServiceImplements(printedElementRepository,
                readerRepository, movementRepository, authorRepository, elementJoinAuthorRepository, publishCompanyRepository);
    }

    @Test
    void CanGetElements() {
        printedElementServiceImplements.getElements();
        verify(printedElementRepository).findAll();
    }

    @Test
    void getElementInformation() {
        printedElementRepository.deleteAll();
        authorRepository.deleteAll();
        elementJoinAuthorRepository.deleteAll();
        PublishCompany publishCompany = new PublishCompany(1, "company");
        publishCompanyRepository.save(publishCompany);
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        printedElementRepository.save(printedElement);
        Author author = new Author(1, "author");
        authorRepository.save(author);
        ElementJoinAuthor elementJoinAuthor = new ElementJoinAuthor(1, author, printedElement);
        elementJoinAuthorRepository.save(elementJoinAuthor);
        List<AuthorDto> authors = new ArrayList<>();
        authors.add(BuilderToDto.toDto(author));
        AllDataOfElementDto allDataOfElementDto = new AllDataOfElementDto(printedElement.getElementId(),
                printedElement.getTitle(), printedElement.getType(), printedElement.getStyle(),
                printedElement.getAmountOfElements(), printedElement.getYearOfPublish(), printedElement.getNumOfPublish(),
                publishCompany.getPublishCompanyId(), publishCompany.getNameOfCompany(), authors);
       // assertThrows()
        assertEquals(allDataOfElementDto, printedElementServiceImplements.getElementInformation(1));
    }

    @Test
    void getElementsByStyle() {
    }

    @Test
    void getElementsByType() {
    }

    @Test
    void CanAddElement() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author = new Author(1, "author");
        ElementJoinAuthor elementJoinAuthor = new ElementJoinAuthor(1, author, printedElement);
        printedElementServiceImplements.addElement(printedElement, author, publishCompany);
        publishCompanyRepository.save(publishCompany);
        //printedElementRepository.save(printedElement);
        //authorRepository.save(author);
        //elementJoinAuthorRepository.save(elementJoinAuthor);
        //verify(printedElementRepository).save(printedElement);
        when(printedElementRepository.save(printedElement)).thenReturn(printedElement);
        PrintedElement element = printedElementRepository.findById(1).get();
        assert(element).equals(printedElement);
    }

    @Test
    void deleteElement() {
    }

    @Test
    void getStatusAndDate() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void showStyles() {
    }

    @Test
    void showTypes() {
    }
}
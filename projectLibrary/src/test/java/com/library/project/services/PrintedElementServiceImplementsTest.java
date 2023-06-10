package com.library.project.services;

import com.library.project.dto.*;
import com.library.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.library.project.exceptions.ApiRequestExceptionNotFound;
import com.library.project.mappers.BuilderToDto;
import com.library.project.models.*;
import com.library.project.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrintedElementServiceImplementsTest {
    @Mock
    private PrintedElementRepository printedElementRepository;
    @Mock
    private ElementJoinAuthorService elementJoinAuthorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ElementJoinAuthorRepository elementJoinAuthorRepository;
    @InjectMocks
    private PrintedElementServiceImplements printedElementServiceImplements;
    @Mock
    private PublishCompanyRepository publishCompanyRepository;

    @Test
    void CanGetElements() {
        printedElementServiceImplements.getElements();
        verify(printedElementRepository).findAll();
    }

    @Test
    void getElementInformation() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author = new Author(1, "author");
        ElementJoinAuthor elementJoinAuthor = new ElementJoinAuthor(1, author, printedElement);
        when(printedElementRepository.findById(1)).thenReturn(Optional.of(printedElement));
        when(elementJoinAuthorService.getDataByPrintedElement(printedElement)).thenReturn(List.of(BuilderToDto.toDto(elementJoinAuthor)));
        AllDataOfElementDto dto = printedElementServiceImplements.getElementInformation(1);
        assertEquals(printedElement.getElementId(), dto.getElementId());
        assertEquals(printedElement.getTitle(), dto.getTitle());
        assertEquals(printedElement.getType(), dto.getType());
        assertEquals(printedElement.getStyle(), dto.getStyle());
        assertEquals(printedElement.getAmountOfElements(), dto.getAmountOfElements());
        assertEquals(printedElement.getNumOfPublish(), dto.getNumOfPublish());
        assertEquals(printedElement.getYearOfPublish(), dto.getYearOfPublish());
        assertEquals(publishCompany.getPublishCompanyId(), dto.getPublishCompanyId());
        assertEquals(publishCompany.getNameOfCompany(), dto.getPublishCompanyName());
        assertEquals(1, dto.getAuthors().size());
        assertEquals(author.getName(), dto.getAuthors().get(0).getName());
        assertEquals(author.getAuthorId(), dto.getAuthors().get(0).getAuthorId() );
    }

    @Test
    void getElementInformationExceptionThrown() {
        printedElementRepository.deleteAll();
        authorRepository.deleteAll();
        elementJoinAuthorRepository.deleteAll();
        when(printedElementRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.getElementInformation(1);
        });
    }


    @Test
    void getElementsByStyle() {
        printedElementRepository.deleteAll();
        authorRepository.deleteAll();
        elementJoinAuthorRepository.deleteAll();
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        List<PrintedElement> listElements = new ArrayList<>(List.of(printedElement));
        when(printedElementRepository.findAll()).thenReturn(listElements);
        List<PrintedElementDto> dtoList = printedElementServiceImplements.getElementsByStyle("Fantasy");
        PrintedElementDto dto = dtoList.get(0);
        assertEquals(1, dtoList.size());
        assertEquals(printedElement.getElementId(), dto.getElementId());
        assertEquals(printedElement.getTitle(), dto.getTitle());
        assertEquals(printedElement.getType(), dto.getType());
        assertEquals(printedElement.getStyle(), dto.getStyle());
        assertEquals(printedElement.getAmountOfElements(), dto.getAmountOfElements());
        assertEquals(printedElement.getNumOfPublish(), dto.getNumOfPublish());
        assertEquals(printedElement.getYearOfPublish(), dto.getYearOfPublish());
        assertEquals(publishCompany.getPublishCompanyId(), dto.getPublishCompanyId());
        assertEquals(publishCompany.getNameOfCompany(), dto.getPublishCompanyName());
    }

    @Test
    void getElementsByStyleExceptionThrown() {
        when(printedElementRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.getElementsByStyle("Fantasy");
        });
    }

    @Test
    void getElementsByType() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        List<PrintedElement> listElements = new ArrayList<>(List.of(printedElement));
        when(printedElementRepository.findAll()).thenReturn(listElements);
        List<PrintedElementDto> dtoList = printedElementServiceImplements.getElementsByType("book");
        PrintedElementDto dto = dtoList.get(0);
        assertEquals(1, dtoList.size());
        assertEquals(printedElement.getElementId(), dto.getElementId());
        assertEquals(printedElement.getTitle(), dto.getTitle());
        assertEquals(printedElement.getType(), dto.getType());
        assertEquals(printedElement.getStyle(), dto.getStyle());
        assertEquals(printedElement.getAmountOfElements(), dto.getAmountOfElements());
        assertEquals(printedElement.getNumOfPublish(), dto.getNumOfPublish());
        assertEquals(printedElement.getYearOfPublish(), dto.getYearOfPublish());
        assertEquals(publishCompany.getPublishCompanyId(), dto.getPublishCompanyId());
        assertEquals(publishCompany.getNameOfCompany(), dto.getPublishCompanyName());
    }

    @Test
    void getElementsByTypeExceptionThrown() {
        when(printedElementRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.getElementsByType("book");
        });
    }

    @Test
    void addElementExceptionThrown() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author = new Author(1, "author");
        ElementDataDto elementDataDto = new ElementDataDto(printedElement.getElementId(),
                printedElement.getTitle(), printedElement.getType(), printedElement.getStyle(),
                printedElement.getAmountOfElements(), printedElement.getYearOfPublish(),
                printedElement.getNumOfPublish(), publishCompany.getPublishCompanyId(),
                publishCompany.getNameOfCompany(), author.getAuthorId(), author.getName());
        when(printedElementRepository.findById(1)).thenReturn(Optional.of(printedElement));
        assertThrows(ApiRequestExceptionAlreadyReported.class, () -> {
            printedElementServiceImplements.addElement(elementDataDto);
        });
        verify(printedElementRepository, never()).save(any(PrintedElement.class));
        verify(publishCompanyRepository, never()).save(any(PublishCompany.class));
        verify(authorRepository, never()).save(any(Author.class));
        verify(elementJoinAuthorRepository, never()).save(any(ElementJoinAuthor.class));
    }

    @Test
    void getStatusAndDateThrownException(){
        when(printedElementRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.getStatusAndDate(1);
        });
    }

    @Test
    void showStyles() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        List<PrintedElement> elements = new ArrayList<>(List.of(printedElement));
        when(printedElementRepository.findAll()).thenReturn(elements);
        List<String> styles = printedElementServiceImplements.showStyles();
        assertEquals(elements.get(0).getStyle(), styles.get(0));
    }

    @Test
    void showTypes() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        List<PrintedElement> elements = new ArrayList<>(List.of(printedElement));
        when(printedElementRepository.findAll()).thenReturn(elements);
        List<String> types = printedElementServiceImplements.showTypes();
        assertEquals(elements.get(0).getType(), types.get(0));
    }
}
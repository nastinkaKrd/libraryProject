package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.AllDataOfElementDto;
import com.libraryProject.project.dto.MovementDto;
import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotFound;
import com.libraryProject.project.models.*;
import com.libraryProject.project.repositories.*;
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
    private ReaderRepository readerRepository;
    @Mock
    private MovementRepository movementRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ElementJoinAuthorRepository elementJoinAuthorRepository;
    @InjectMocks
    private PrintedElementServiceImplements printedElementServiceImplements;
    @Mock
    private PublishCompanyRepository publishCompanyRepository;
   /* @BeforeEach
    void setUp() {
        printedElementServiceImplements = new PrintedElementServiceImplements(printedElementRepository,
                readerRepository, movementRepository, authorRepository, elementJoinAuthorRepository, publishCompanyRepository);
    }*/

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
        when(elementJoinAuthorRepository.findAllByPrintedElementId(printedElement)).thenReturn(List.of(elementJoinAuthor));
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
    void CanAddElement() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author = new Author(1, "author");
        ElementJoinAuthor elementJoinAuthor = new ElementJoinAuthor(1, author, printedElement);

        PublishCompany publishCompany2 = new PublishCompany(2, "company");
        PrintedElement printedElement2 = new PrintedElement(2, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author2 = new Author(2, "author");
        ElementJoinAuthor elementJoinAuthor2 = new ElementJoinAuthor(2, author2, printedElement2);

        PublishCompany publishCompany3 = new PublishCompany(3, "company");
        PrintedElement printedElement3 = new PrintedElement(3, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author3 = new Author(3, "author");
        ElementJoinAuthor elementJoinAuthor3 = new ElementJoinAuthor(3, author3, printedElement3);

        when(elementJoinAuthorRepository.findAll()).thenReturn(List.of(elementJoinAuthor, elementJoinAuthor2));
        when(publishCompanyRepository.findById(3)).thenReturn(Optional.empty());
        when(printedElementRepository.findById(3)).thenReturn(Optional.empty());
        when(authorRepository.findById(3)).thenReturn(Optional.empty());


        printedElementServiceImplements.addElement(printedElement3, author3, publishCompany3);

        verify(publishCompanyRepository).save(publishCompany3);
        verify(printedElementRepository).save(printedElement3);
        verify(authorRepository).save(author3);
        verify(elementJoinAuthorRepository).save(elementJoinAuthor3);
    }

    @Test
    void addElementExceptionThrown() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Author author = new Author(1, "author");
        when(printedElementRepository.findById(1)).thenReturn(Optional.of(printedElement));
        assertThrows(ApiRequestExceptionAlreadyReported.class, () -> {
            printedElementServiceImplements.addElement(printedElement, author, publishCompany);
        });
        verify(printedElementRepository, never()).save(any(PrintedElement.class));
        verify(publishCompanyRepository, never()).save(any(PublishCompany.class));
        verify(authorRepository, never()).save(any(Author.class));
        verify(elementJoinAuthorRepository, never()).save(any(ElementJoinAuthor.class));
    }


    @Test
    void deleteElement() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Reader reader = new Reader(1, "reader");
        List<Movement> movements = List.of(new Movement(1, "taked", new Date(2012, 11, 21), reader, printedElement));
        List<ElementJoinAuthor> elementJoinAuthors = List.of(new ElementJoinAuthor
                (1, new Author(1, "author"), printedElement));

        when(printedElementRepository.findById(1)).thenReturn(Optional.of(printedElement));
        when(movementRepository.findAllByPrintedElementId(printedElement)).thenReturn(movements);
        when(elementJoinAuthorRepository.findAllByPrintedElementId(printedElement)).thenReturn(elementJoinAuthors);
        printedElementServiceImplements.deleteElement(1);
        verify(movementRepository, times(1)).deleteById(movements.get(0).getMoveId());
        verify(elementJoinAuthorRepository, times(1)).deleteById(elementJoinAuthors.get(0).getJoinId());
        verify(printedElementRepository, times(1)).deleteById(1);
    }

    @Test
    void getStatusAndDate() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                1, 2012, 3, publishCompany);
        Reader reader = new Reader(1, "reader");
        Movement movement = new Movement(1, "taked", new Date(2012, 11, 21), reader, printedElement);
        List<MovementDto> movementDtos = new ArrayList<>(List.of(BuilderToDto.toDto(new Movement(1, "taked", new Date(2012, 11, 21), reader, printedElement))));
        when(printedElementRepository.findById(1)).thenReturn(Optional.of(printedElement));
        when(movementRepository.findAllByPrintedElementId(printedElement)).thenReturn(new ArrayList<>(List.of(movement)));
        List<MovementDto> serviceData = printedElementServiceImplements.getStatusAndDate(1);
        assertEquals(serviceData.get(0), movementDtos.get(0));
    }

    @Test
    void getStatusAndDateThrownException(){
        when(printedElementRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.getStatusAndDate(1);
        });
    }

    @Test
    void changeStatus() {
        PublishCompany publishCompany = new PublishCompany(1, "company");
        PrintedElement printedElement = new PrintedElement(1, "title", "book", "Fantasy",
                11, 2012, 3, publishCompany);
        Reader reader = new Reader(1, "reader");
        Movement movement = new Movement(1, "returned", new Date(2012, 11, 21), reader, printedElement);

        PublishCompany publishCompany2 = new PublishCompany(2, "company");
        PrintedElement printedElement2 = new PrintedElement(2, "title", "book", "Fantasy",
                11, 2012, 3, publishCompany2);
        Reader reader2 = new Reader(2, "reader");
        Movement movement2 = new Movement(2, "taked 1", new Date(2012, 11, 21), reader2, printedElement2);

        List<Movement> movements = new ArrayList<>(List.of(movement, movement2));

        when(printedElementRepository.findById(1)).thenReturn(Optional.of(printedElement));
        when(printedElementRepository.findById(2)).thenReturn(Optional.of(printedElement2));
        when(readerRepository.findById(1)).thenReturn(Optional.of(reader));
        when(readerRepository.findById(2)).thenReturn(Optional.of(reader2));
        when(movementRepository.findAllByPrintedElementId(movement.getPrintedElementId())).thenReturn(List.of(movement));
        when(movementRepository.findAll()).thenReturn(movements);
        MovementDto movementDto = BuilderToDto.toDto(movement);
        MovementDto movementDto1 = printedElementServiceImplements.changeStatus(movement.getPrintedElementId().getElementId()
                , "taked 1", movement.getReaderId().getReaderId());
        verify(printedElementRepository).findById(1);
        verify(readerRepository).findById(1);
        verify(movementRepository).findAllByPrintedElementId(printedElement);
        verify(movementRepository).findAll();
        verify(movementRepository, times(3)).save(any(Movement.class));
        verify(movementRepository).findById(1);
        assertEquals(movementDto, movementDto1);
    }

    @Test
    void changeStatusAndDateThrownException1(){
        when(printedElementRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.changeStatus(1, "returned", 1);
        });
    }
    @Test
    void changeStatusAndDateThrownException2(){
        when(readerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            printedElementServiceImplements.changeStatus(1, "returned", 1);
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
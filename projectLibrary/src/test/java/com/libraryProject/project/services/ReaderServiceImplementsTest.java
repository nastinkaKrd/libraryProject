package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.models.*;
import com.libraryProject.project.repositories.MovementRepository;
import com.libraryProject.project.repositories.PrintedElementRepository;
import com.libraryProject.project.repositories.PublishCompanyRepository;
import com.libraryProject.project.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderServiceImplementsTest {

    @Mock
    private ReaderRepository readerRepository;
    private ReaderServiceImplements readerService;
    @Mock
    private MovementRepository movementRepository;
    @Mock
    private PrintedElementRepository printedElementRepository;
    @Mock
    private PublishCompanyRepository publishCompanyRepository;
    @BeforeEach
    void setUp(){
        readerService = new ReaderServiceImplements(readerRepository, movementRepository);
    }
    @Test
    void getReaders() {
        readerService.getReaders();
        verify(readerRepository).findAll();
    }

    @Test
    void canGetElements() {
        Reader reader = new Reader(1, "Wert Yels");
        PublishCompany publishCompany = new PublishCompany(1, "Ballantine Books");
        PrintedElement element = new PrintedElement(40, "some book3", "book", "Fantasy",
                14, 2021, 2, publishCompany);
        Date date = new Date();
        java.sql.Date sqlDdate = new java.sql.Date(date.getTime());
        Movement movement = new Movement(489489999, "taked 2", sqlDdate, reader, element);
      // when(movementRepository.deleteAll()).thenReturn(Op);
        printedElementRepository.deleteAll();
        publishCompanyRepository.deleteAll();
        readerRepository.deleteAll();
        publishCompanyRepository.save(publishCompany);
        printedElementRepository.save(element);
        readerRepository.save(reader);
        movementRepository.save(movement);
        PrintedElementDto dto1 = BuilderToDto.toDto(element);
        PrintedElementDto dto = readerService.getElements(reader.getReaderId()).get(0);

        assertEquals(dto, dto1);

        verify(movementRepository).deleteAll();
        verifyNoMoreInteractions(movementRepository);
    }

    @Test
    void canAddReader() {
        Reader reader = new Reader(1, "reader 1");
        readerService.addReader(reader.getName(), reader.getReaderId());
        ArgumentCaptor<Reader> readerArgumentCapture = ArgumentCaptor.forClass(Reader.class);
        verify(readerRepository).save(readerArgumentCapture.capture());
        Reader capturedReader = readerArgumentCapture.getValue();
        assert(capturedReader).equals(reader);
    }

    @Test
    void deleteReader() {
        Reader reader = new Reader(1, "reader 1");
        readerService.addReader(reader.getName(), reader.getReaderId());
        readerService.deleteReader(1);
        ArgumentCaptor<Reader> readerArgumentCapture = ArgumentCaptor.forClass(Reader.class);
        verify(readerRepository).delete(readerArgumentCapture.capture());

    }
}
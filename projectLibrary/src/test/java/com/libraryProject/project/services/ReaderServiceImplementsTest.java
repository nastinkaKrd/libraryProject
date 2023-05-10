package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.dto.ReaderDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotFound;
import com.libraryProject.project.models.*;
import com.libraryProject.project.repositories.MovementRepository;
import com.libraryProject.project.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderServiceImplementsTest {

    @Mock
    private ReaderRepository readerRepository;
    @InjectMocks
    private ReaderServiceImplements readerService;
    @Mock
    private MovementRepository movementRepository;
    @BeforeEach
    void setUp(){
        readerService = new ReaderServiceImplements(readerRepository, movementRepository);
    }
    @Test
    void getReaders() {
        List<Reader> readers = new ArrayList<>(List.of(
                new Reader(1, "name 1"),
                new Reader(2, "name 2"),
                new Reader(3, "name 3")));
        List<ReaderDto> dtos = new ArrayList<>(List.of(
                new ReaderDto(1, "name 1"),
                new ReaderDto(2, "name 2"),
                new ReaderDto(3, "name 3")));
        when(readerRepository.findAll()).thenReturn(readers);
        List<ReaderDto> readerDtos = readerService.getReaders();
        assertEquals(readerDtos, dtos);
        verify(readerRepository).findAll();
    }

    @Test
    void canGetElements() {
        Reader reader = new Reader(1, "Wert Yels");
        PublishCompany publishCompany = new PublishCompany(1, "Ballantine Books");
        PrintedElement element = new PrintedElement(1, "some book3", "book", "Fantasy",
                14, 2021, 2, publishCompany);
        Movement movement = new Movement(1, "taked 2", new Date(2010, 11, 11), reader, element);
        List<Movement> movements = new ArrayList<>(List.of(movement));
        when(readerRepository.findById(1)).thenReturn(Optional.of(reader));
        when(movementRepository.findAllByReaderId(reader)).thenReturn(movements);
        List<PrintedElementDto> elementDtos = readerService.getElements(reader.getReaderId());
        assertEquals(elementDtos.get(0), BuilderToDto.toDto(element));
        verify(readerRepository).findById(1);
        verify(movementRepository).findAllByReaderId(reader);
    }

    @Test
    public void getElementsThrownException() {
        when(readerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            readerService.getElements(1);
        });
        verify(movementRepository, never()).findAllByReaderId(new Reader(1, "reader"));
    }

    @Test
    void canAddReader() {
        Reader reader = new Reader(1, "reader 1");
        when(readerRepository.findById(1)).thenReturn(Optional.empty());
        readerService.addReader(reader.getName(), reader.getReaderId());
        verify(readerRepository).save(reader);
    }

    @Test
    public void addReaderThrownException() {
        Reader reader = new Reader(1, "reader 1");
        when(readerRepository.findById(1)).thenReturn(Optional.of(reader));
        assertThrows(ApiRequestExceptionAlreadyReported.class, () -> {
            readerService.addReader(reader.getName(), reader.getReaderId());
        });
        verify(readerRepository, never()).save(reader);
    }

    @Test
    void deleteReader() {
        Reader reader = new Reader(1, "reader 1");
        PublishCompany publishCompany = new PublishCompany(1, "Ballantine Books");
        PrintedElement element = new PrintedElement(1, "some book3", "book", "Fantasy",
                14, 2021, 2, publishCompany);

        Movement movement = new Movement(1, "taked", new Date(2010, 11, 11), reader, element);
        List<Movement> movements = new ArrayList<>(List.of(movement));
        when(readerRepository.findById(1)).thenReturn(Optional.of(reader));
        when(movementRepository.findAllByReaderId(reader)).thenReturn(movements);
        readerService.deleteReader(reader.getReaderId());
        for (Movement temp : movements){
            verify(movementRepository).deleteById(temp.getMoveId());
        }
        verify(readerRepository).delete(reader);
    }

    @Test
    public void deleteReaderThrownException() {
        when(readerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            readerService.deleteReader(1);
        });
        verify(movementRepository, never()).deleteById(1);
    }
}
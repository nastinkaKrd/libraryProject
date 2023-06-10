package com.library.project.services;

import com.library.project.dto.MovementDto;
import com.library.project.dto.ReaderDto;
import com.library.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.library.project.exceptions.ApiRequestExceptionNotFound;
import com.library.project.models.PublishCompany;
import com.library.project.models.Reader;
import com.library.project.repositories.ReaderRepository;
import com.library.project.dto.PrintedElementDto;
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
    @Mock
    private MovementService movementService;
    @InjectMocks
    private ReaderServiceImplements readerService;
    @BeforeEach
    void setUp(){
        readerService = new ReaderServiceImplements(readerRepository, movementService);
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
        ReaderDto reader = new ReaderDto(1, "Wert Yels");
        Reader readerModel = new Reader(1, "Wert Yels");
        PrintedElementDto element = new PrintedElementDto(1, "some book3", "book", "Fantasy",
                14, 2021, 2, 1, "Ballantine Books");
        MovementDto movement = new MovementDto(1, "taked 2", new Date(2010, 11, 11), reader, element);
        List<MovementDto> movements = new ArrayList<>(List.of(movement));
        when(readerRepository.findById(1)).thenReturn(Optional.of(readerModel));
        when(movementService.getDataByReader(reader)).thenReturn(movements);
        List<PrintedElementDto> elementDtos = readerService.getElements(reader.getReaderId());
        assertEquals(elementDtos.get(0), element);
        verify(readerRepository).findById(1);
    }

    @Test
    public void getElementsThrownException() {
        when(readerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> readerService.getElements(1));
        verify(movementService, never()).getDataByReader(new ReaderDto(1, "reader"));
    }

    @Test
    void canAddReader() {
        Reader reader1 = new Reader(1, "reader 1");
        when(readerRepository.findByName(reader1.getName())).thenReturn(Optional.empty());
        readerService.addReader(reader1.getName());
        verify(readerRepository).save(reader1);
    }

    @Test
    public void addReaderThrownException() {
        Reader reader = new Reader(1, "reader 1");
        when(readerRepository.findByName(reader.getName())).thenReturn(Optional.of(reader));
        assertThrows(ApiRequestExceptionAlreadyReported.class, () -> {
            readerService.addReader(reader.getName());
        });
        verify(readerRepository, never()).save(reader);
    }

    @Test
    void deleteReader() {
        Reader reader = new Reader(1, "reader 1");
        ReaderDto readerDto = new ReaderDto(1, "reader 1");
        PublishCompany publishCompany = new PublishCompany(1, "Ballantine Books");
        PrintedElementDto element = new PrintedElementDto(1, "some book3", "book", "Fantasy",
                14, 2021, 2, 1, "Ballantine Books");
        MovementDto movement = new MovementDto(1, "taked", new Date(2010, 11, 11), readerDto, element);
        List<MovementDto> movements = new ArrayList<>(List.of(movement));
        when(readerRepository.findById(1)).thenReturn(Optional.of(reader));
        when(movementService.getDataByReader(readerDto)).thenReturn(movements);
        readerService.deleteReader(reader.getReaderId());
        for (MovementDto temp : movements){
            verify(movementService).deleteById(temp.getMoveId());
        }
        verify(readerRepository).delete(reader);
    }

    @Test
    public void deleteReaderThrownException() {
        when(readerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ApiRequestExceptionNotFound.class, () -> {
            readerService.deleteReader(1);
        });
        verify(movementService, never()).deleteById(1);
    }
}
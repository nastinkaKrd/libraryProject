package com.library.project.services;

import com.library.project.dto.ReaderDto;
import com.library.project.dto.PrintedElementDto;
import com.library.project.models.Reader;
import java.util.List;
import java.util.Optional;

public interface ReaderService {
    Optional<Reader> getById(int id);
    List<ReaderDto> getReaders();

    List<PrintedElementDto> getElements(int id) ;

    void addReader(String name);

    void deleteReader(int id);

}

package com.libraryProject.project.services;

import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.dto.ReaderDto;
import com.libraryProject.project.models.PrintedElement;

import java.util.List;

public interface ReaderService {
    List<ReaderDto> getReaders();

    List<PrintedElementDto> getElements(int id) ;

    void addReader(String name, int id);

    void deleteReader(int id);

}

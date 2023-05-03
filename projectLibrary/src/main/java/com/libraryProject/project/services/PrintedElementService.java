package com.libraryProject.project.services;

import com.libraryProject.project.dto.AllDataOfElementDto;
import com.libraryProject.project.dto.MovementDto;
import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.models.Author;
import com.libraryProject.project.models.PrintedElement;
import com.libraryProject.project.models.PublishCompany;

import java.util.List;

public interface PrintedElementService {
    List<PrintedElementDto> getElements();
    void addElement(PrintedElement element, Author author, PublishCompany publishCompany);

    AllDataOfElementDto getElementInformation(int elementId);

    List<PrintedElementDto> getElementsByStyle(String style);

    List<PrintedElementDto> getElementsByType(String type);

    List<MovementDto> getStatusAndDate(int elementId);

    MovementDto changeStatus(int elementId, String status, int readerId);

    void deleteElement(int id);

    List<String> showStyles();

    List<String> showTypes();
}

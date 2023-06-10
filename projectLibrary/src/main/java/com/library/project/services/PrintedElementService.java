package com.library.project.services;

import com.library.project.dto.AllDataOfElementDto;
import com.library.project.dto.ElementDataDto;
import com.library.project.dto.MovementDto;
import com.library.project.dto.PrintedElementDto;
import java.util.List;

public interface PrintedElementService {
    List<PrintedElementDto> getElements();
    void addElement(ElementDataDto printedElement);

    AllDataOfElementDto getElementInformation(int elementId);

    List<PrintedElementDto> getElementsByStyle(String style);

    List<PrintedElementDto> getElementsByType(String type);

    List<MovementDto> getStatusAndDate(int elementId);

    MovementDto changeStatus(int elementId, String status, int readerId);

    void deleteElement(int id);

    List<String> showStyles();

    List<String> showTypes();
}

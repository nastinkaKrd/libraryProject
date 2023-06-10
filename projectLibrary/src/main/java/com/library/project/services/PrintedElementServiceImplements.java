package com.library.project.services;

import com.library.project.dto.*;
import com.library.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.library.project.exceptions.ApiRequestExceptionChangeStatus;
import com.library.project.exceptions.ApiRequestExceptionNotFound;
import com.library.project.mappers.BuilderToDto;
import com.library.project.models.PrintedElement;
import com.library.project.models.Reader;
import com.library.project.repositories.PrintedElementRepository;
import com.library.project.mappers.BuilderToModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
public class PrintedElementServiceImplements implements PrintedElementService {
    PrintedElementRepository printedElementRepository;
    MovementService movementService;
    ReaderService readerService;
    AuthorService authorService;
    PublishCompanyService publishCompanyService;
    ElementJoinAuthorService elementJoinAuthorService;
    @Override
    public List<PrintedElementDto> getElements() {
        List<PrintedElement> elements = printedElementRepository.findAll();
        List<PrintedElementDto> elementsDto = new ArrayList<>();
        elements.forEach(element -> elementsDto.add(BuilderToDto.toDto(element)));
        return elementsDto;
    }

    @Override
    public AllDataOfElementDto getElementInformation(int id) {
        Optional<PrintedElement> printedElement = printedElementRepository.findById(id);
        List<AuthorDto> authorDtos = new ArrayList<>();
        if (printedElement.isPresent()){
            for (ElementJoinAuthorDto temp: elementJoinAuthorService.getDataByPrintedElement(printedElement.get())){
                authorDtos.add(new AuthorDto(temp.getAuthorId(), temp.getAuthorName()));
            }
            return BuilderToDto.toDto(BuilderToDto.toDto(printedElement.get()), authorDtos);
        }else {
            throw new ApiRequestExceptionNotFound("element is not found");
        }
    }

    @Override
    public List<PrintedElementDto> getElementsByStyle(String style){
        List<PrintedElement> elements = printedElementRepository.findAll();
        List<PrintedElementDto> elementsDto = new ArrayList<>();
        elements.forEach(element -> {
            if (element.getStyle().equals(style)){
                elementsDto.add(BuilderToDto.toDto(element));
            }
        });
        if (elementsDto.isEmpty()){
            throw new ApiRequestExceptionNotFound("there are nothing elements with this filter");
        }
        return elementsDto;
    }

    @Override
    public List<PrintedElementDto> getElementsByType(String type) {
        List<PrintedElement> elements = printedElementRepository.findAll();
        List<PrintedElementDto> elementsDto = new ArrayList<>();
        elements.forEach(element -> {
            if (element.getType().equals(type)){
                elementsDto.add(BuilderToDto.toDto(element));
            }
        });
        if (elementsDto.isEmpty()){
            throw new ApiRequestExceptionNotFound("there are nothing elements with this filter");
        }
        return elementsDto;
    }

    @Override
    public void addElement(ElementDataDto printedElement) {
        PublishCompanyDto publishCompany = new PublishCompanyDto(printedElement.getPublishCompanyId(),
                printedElement.getPublishCompanyName());
        PrintedElementDto element = new PrintedElementDto(printedElement.getElementId(), printedElement.getTitle(),
                printedElement.getType(), printedElement.getStyle(), printedElement.getAmountOfElements(),
                printedElement.getYearOfPublish(), printedElement.getNumOfPublish(), printedElement.getPublishCompanyId(),
                printedElement.getPublishCompanyName());
        AuthorDto author = new AuthorDto(printedElement.getAuthorId(), printedElement.getAuthorName());

        if (printedElementRepository.findById(element.getElementId()).isEmpty()) {
            if (publishCompanyService.getById(publishCompany.getPublishCompanyId()).isEmpty()){
                publishCompanyService.savePublishCompany(publishCompany);
            }
            printedElementRepository.save(BuilderToModel.toModel(element));
            List<ElementJoinAuthorDto> data = elementJoinAuthorService.getAllData();
            if (authorService.getById(author.getAuthorId()).isPresent()){
                ElementJoinAuthorDto elementJoinAuthorDto = new ElementJoinAuthorDto( (data.get(data.size()-1).getJoinId()+1),
                        element.getElementId(), element.getTitle(), element.getType(), element.getStyle(),
                element.getAmountOfElements(), element.getYearOfPublish(), element.getNumOfPublish(), publishCompany.getPublishCompanyId(),
                publishCompany.getNameOfCompany(), author.getAuthorId(), author.getName());
                elementJoinAuthorService.saveData(elementJoinAuthorDto);
                }else{
                authorService.saveAuthor(author);
                ElementJoinAuthorDto elementJoinAuthorDto = new ElementJoinAuthorDto( (data.get(data.size()-1).getJoinId()+1),
                        element.getElementId(), element.getTitle(), element.getType(), element.getStyle(),
                        element.getAmountOfElements(), element.getYearOfPublish(), element.getNumOfPublish(), publishCompany.getPublishCompanyId(),
                        publishCompany.getNameOfCompany(), author.getAuthorId(), author.getName());
                elementJoinAuthorService.saveData(elementJoinAuthorDto);
                }
        }
        else {
            throw new ApiRequestExceptionAlreadyReported("Element was added before, not now");
        }
    }

    @Override
    public void deleteElement(int id){
        Optional<PrintedElement> element = printedElementRepository.findById(id);
        if (element.isPresent()) {
            List<MovementDto> listOfId = movementService.getDataByElements(BuilderToDto.toDto(element.get()));
            if (!listOfId.isEmpty()){
                for (MovementDto temp: listOfId){
                    movementService.deleteById(temp.getMoveId());
                }
            }
            List<ElementJoinAuthorDto> joinTableList = elementJoinAuthorService.getDataByPrintedElement(element.get());
            if (!joinTableList.isEmpty()){
                for (ElementJoinAuthorDto temp: joinTableList){
                    elementJoinAuthorService.deleteDataById(temp.getJoinId());
                }
            }
            printedElementRepository.deleteById(id);
        }
        else {
            throw new ApiRequestExceptionNotFound("Element is not found");
        }
    }

    @Override
    public List<MovementDto> getStatusAndDate(int elementId) {
        Optional<PrintedElement> element = printedElementRepository.findById(elementId);
        if (element.isPresent()){
            return movementService.getDataByElements(BuilderToDto.toDto(element.get()));
        }else {
            throw new ApiRequestExceptionNotFound("Element is not found");
        }

    }

    @Override
    public MovementDto changeStatus(int elementId, String status, int readerId){
        Optional<PrintedElement> element = printedElementRepository.findById(elementId);
        Optional<Reader> reader = readerService.getById(readerId);
        final String taked = "taked";
        if (element.isPresent() && reader.isPresent()) {
            ReaderDto readerDto = BuilderToDto.toDto(reader.get());
            PrintedElementDto printedElementDto = BuilderToDto.toDto(element.get());
            Date date = new Date();
            java.sql.Date sqlDdate = new java.sql.Date(date.getTime());
            boolean isUpdated = false;
            List<MovementDto> data = movementService.getDataByElements(BuilderToDto.toDto(element.get()));
            for (MovementDto temp: data){
                if(status.equals("returned") && !temp.getStatus().equals("returned")){
                    if (temp.getReader()==readerDto){
                        MovementDto movement = new MovementDto(temp.getMoveId(), status, sqlDdate, readerDto, printedElementDto);
                        movementService.saveData(movement);
                        isUpdated = true;
                        break;
                    }
                }else if (status.startsWith(taked) && !temp.getStatus().startsWith(taked)){
                    MovementDto movement = new MovementDto(temp.getMoveId(), status, sqlDdate, readerDto, printedElementDto);
                    movementService.saveData(movement);
                    isUpdated = true;
                    break;
                }
            }

            List<MovementDto> movements = movementService.getElementsAndReaders();
            int movementId = movements.get(movements.size()-1).getMoveId()+1;
            if (status.startsWith(taked) && !isUpdated){
                int count = 0;
                String tempString;
                for(MovementDto temp: data){
                    tempString = temp.getStatus();
                    if(tempString.startsWith(taked)){
                        if(tempString.length()==5){
                            count++;
                        }else {
                            count += Integer.parseInt(tempString.substring(6));
                        }
                    }
                }
                count += Integer.parseInt(status.substring(6));
                if(count <= element.get().getAmountOfElements()){
                    MovementDto movement = new MovementDto(movementId, status, sqlDdate, readerDto, printedElementDto);
                    movementService.saveData(movement);
                    isUpdated = true;
                }else {
                    throw new ApiRequestExceptionChangeStatus("You can't take this book/magazine, there aren't enough books/magazines");
                }
            }
            if(!isUpdated && status.startsWith(taked) && element.get().getAmountOfElements() >= Integer.parseInt(status.substring(6))){
                MovementDto movement = new MovementDto(movementId, status, sqlDdate, readerDto, printedElementDto);
                movementService.saveData(movement);
                isUpdated = true;
            }
            if(!isUpdated) {
                throw new ApiRequestExceptionChangeStatus("Status couldn't be changed");
            }
            List<MovementDto> movements2 = movementService.getDataByElements(printedElementDto);
            int moveId = Integer.MAX_VALUE;
            for(MovementDto temp: movements2){
                if (temp.getStatus().equals(status) && temp.getDate().equals(date) && temp.getReader().equals(readerDto)
                        && temp.getPrintedElement().equals(printedElementDto)){
                    moveId = temp.getMoveId();
                    break;
                }
            }
            return movementService.getDataById(moveId);
        }
        else {
            throw new ApiRequestExceptionNotFound("Element or reader is not found");
        }
    }

    @Override
    public List<String> showStyles(){
        List<PrintedElement> elements = printedElementRepository.findAll();
        List<String> styles = new ArrayList<>();
        for (PrintedElement element : elements){
            styles.add(element.getStyle());
        }
        return styles.stream().distinct().toList();
    }


    @Override
    public List<String> showTypes() {
        List<PrintedElement> elements = printedElementRepository.findAll();
        List<String> types = new ArrayList<>();
        for (PrintedElement element : elements){
            types.add(element.getType());
        }
        return types.stream().distinct().toList();
    }
}
   

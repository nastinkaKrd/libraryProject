package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.AllDataOfElementDto;
import com.libraryProject.project.dto.AuthorDto;
import com.libraryProject.project.dto.MovementDto;
import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.exceptions.ApiRequestExceptionChangeStatus;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotFound;
import com.libraryProject.project.models.*;
import com.libraryProject.project.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
public class PrintedElementServiceImplements implements PrintedElementService {
    PrintedElementRepository printedElementRepository;
    ReaderRepository readerRepository;
    MovementRepository movementRepository;
    AuthorRepository authorRepository;
    ElementJoinAuthorRepository elementJoinAuthorRepository;
    PublishCompanyRepository publishCompanyRepository;
    @Override
    public List<PrintedElementDto> getElements() {
        List<PrintedElement> elements = printedElementRepository.findAll();
        List<PrintedElementDto> elementsDto = new ArrayList<>();
        elements.stream().forEach(element -> {
            elementsDto.add(BuilderToDto.toDto(element));
        });
        return elementsDto;
    }

    @Override
    public AllDataOfElementDto getElementInformation(int id) {
        Optional<PrintedElement> printedElement = printedElementRepository.findById(id);
        List<AuthorDto> authorDtos = new ArrayList<>();
        if (printedElement.isPresent()){
            for (ElementJoinAuthor temp: elementJoinAuthorRepository.findAllByPrintedElementId(printedElement.get())){
                authorDtos.add(BuilderToDto.toDto(temp.getAuthorId()));
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
        elements.stream().forEach(element -> {
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
        elements.stream().forEach(element -> {
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
    public void addElement(PrintedElement element, Author author, PublishCompany publishCompany) {
        if (printedElementRepository.findById(element.getElementId()).isEmpty()) {
            if (publishCompanyRepository.findById(publishCompany.getPublishCompanyId()).isEmpty()){
                publishCompanyRepository.save(publishCompany);
            }
            printedElementRepository.save(element);
            List<ElementJoinAuthor> data = elementJoinAuthorRepository.findAll();
            if (authorRepository.findById(author.getAuthorId()).isPresent()){
                elementJoinAuthorRepository.save(new ElementJoinAuthor((data.get(data.size()-1).getJoinId()+1), author, element));
            }else{
                authorRepository.save(author);
                elementJoinAuthorRepository.save(new ElementJoinAuthor((data.get(data.size()-1).getJoinId()+1), author, element));
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
            List<Movement> listOfId = movementRepository.findAllByPrintedElementId(element.get());
            if (!listOfId.isEmpty()){
                for (Movement temp: listOfId){
                    movementRepository.deleteById(temp.getMoveId());
                }
            }
            List<ElementJoinAuthor> joinTableList = elementJoinAuthorRepository.findAllByPrintedElementId(element.get());
            if (!joinTableList.isEmpty()){
                for (ElementJoinAuthor temp: joinTableList){
                    elementJoinAuthorRepository.deleteById(temp.getJoinId());
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
            List<MovementDto> movementDtos = new ArrayList<>();
            List<Movement> dataOfElement = movementRepository.findAllByPrintedElementId(element.get());
            for (Movement temp: dataOfElement){
                movementDtos.add(BuilderToDto.toDto(temp));
            }
            return movementDtos;
        }else {
            throw new ApiRequestExceptionNotFound("Element is not found");
        }

    }

    @Override
    public MovementDto changeStatus(int elementId, String status, int readerId){
        Optional<PrintedElement> element = printedElementRepository.findById(elementId);
        Optional<Reader> reader = readerRepository.findById(readerId);
        final String taked = "taked";
        if (element.isPresent() && reader.isPresent()) {
            Date date = new Date();
            java.sql.Date sqlDdate = new java.sql.Date(date.getTime());
            boolean isUpdated = false;
            List<Movement> data = movementRepository.findAllByPrintedElementId(element.get());
            for (Movement temp: data){
                if(status.equals("returned") && !temp.getStatus().equals("returned")){
                    if (temp.getReaderId()==reader.get()){
                        movementRepository.save(new Movement(temp.getMoveId(), status, sqlDdate, reader.get(), element.get()));
                        isUpdated = true;
                        break;
                    }
                }else if (status.startsWith(taked) && !temp.getStatus().startsWith(taked)){
                    movementRepository.save(new Movement(temp.getMoveId(), status, sqlDdate, reader.get(), element.get()));
                    isUpdated = true;
                    break;
                }
            }

            List<Movement> movements = movementRepository.findAll();
            int movementId = movements.get(movements.size()-1).getMoveId()+1;
            if (status.startsWith(taked) && !isUpdated){
                int count = 0;
                String tempString;
                for(Movement temp: data){
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
                    movementRepository.save(new Movement(movementId, status, sqlDdate, reader.get(), element.get()));
                    isUpdated = true;
                }else {
                    throw new ApiRequestExceptionChangeStatus("You can't take this book/magazine, there aren't enough books/magazines");
                }
            }
            if(!isUpdated && status.startsWith(taked) && element.get().getAmountOfElements() >= Integer.parseInt(status.substring(6))){
                movementRepository.save(new Movement(movementId, status, sqlDdate, reader.get(), element.get()));
                isUpdated = true;
            }
            if(!isUpdated) {
                throw new ApiRequestExceptionChangeStatus("Status couldn't be changed");
            }
            List<Movement> movements2 = movementRepository.findAllByPrintedElementId(element.get());
            int moveId = Integer.MAX_VALUE;
            for(Movement temp: movements2){
                if (temp.getStatus().equals(status) && temp.getDate().equals(date) && temp.getReaderId().equals(reader.get()) && temp.getPrintedElementId().equals(element.get())){
                    moveId = temp.getMoveId();
                    break;
                }
            }
            return BuilderToDto.toDto(movementRepository.findById(moveId).get());
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
   

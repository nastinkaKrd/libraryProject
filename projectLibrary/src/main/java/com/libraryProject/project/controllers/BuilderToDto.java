package com.libraryProject.project.controllers;

import com.libraryProject.project.dto.*;
import com.libraryProject.project.models.*;

import java.util.List;
import java.util.Set;


public class BuilderToDto {
    static public AuthorDto toDto(Author author){
        return AuthorDto.builder().authorId(author.getAuthorId())
                .name(author.getName()).build();
    }


    static public MovementDto toDto(Movement movement){
        return MovementDto.builder().status(movement.getStatus()).date(movement.getDate()).build();
    }

    static public PrintedElementDto toDto(PrintedElement printedElement){
        return PrintedElementDto.builder().elementId(printedElement.getElementId()).title(printedElement.getTitle()).type(printedElement.getType())
                .style(printedElement.getStyle()).amountOfElements(printedElement.getAmountOfElements()).yearOfPublish(printedElement.getYearOfPublish())
                .numOfPublish(printedElement.getNumOfPublish()).publishCompanyId(printedElement.getPublishCompany().getPublishCompanyId())
                .publishCompanyName(printedElement.getPublishCompany().getNameOfCompany()).build();
    }

    static public ElementJoinAuthorDto toDto(ElementJoinAuthor elementJoinAuthor){
        return ElementJoinAuthorDto.builder().elementId(elementJoinAuthor.getPrintedElementId().getElementId())
                .title(elementJoinAuthor.getPrintedElementId().getTitle()).type(elementJoinAuthor.getPrintedElementId().getType())
                .style(elementJoinAuthor.getPrintedElementId().getStyle()).amountOfElements(elementJoinAuthor.getPrintedElementId().getAmountOfElements())
                .yearOfPublish(elementJoinAuthor.getPrintedElementId().getYearOfPublish())
                .numOfPublish(elementJoinAuthor.getPrintedElementId().getNumOfPublish())
                .publishCompanyId(elementJoinAuthor.getPrintedElementId().getPublishCompany().getPublishCompanyId())
                .publishCompanyName(elementJoinAuthor.getPrintedElementId().getPublishCompany().getNameOfCompany())
                .authorId(elementJoinAuthor.getAuthorId().getAuthorId()).authorName(elementJoinAuthor.getAuthorId().getName()).build();
    }

    static public PublishCompanyDto toDto(PublishCompany publishCompany){
        return PublishCompanyDto.builder().publishCompanyId(publishCompany.getPublishCompanyId()).nameOfCompany(publishCompany.getNameOfCompany()).build();
    }

    static public ReaderDto toDto(Reader reader){

        return ReaderDto.builder().readerId(reader.getReaderId()).name(reader.getName()).build();
    }

    static public UserDto toDto(User user){
        return UserDto.builder().userId(user.getUserId()).userName(user.getUserName()).email(user.getEmail())
                .isLogged(user.isLogged()).build();
    }

    static public AllDataOfElementDto toDto(PrintedElementDto printedElementDto, List<AuthorDto> authors){
        return AllDataOfElementDto.builder().elementId(printedElementDto.getElementId()).title(printedElementDto.getTitle())
                .type(printedElementDto.getType())
                .style(printedElementDto.getStyle()).amountOfElements(printedElementDto.getAmountOfElements())
                .yearOfPublish(printedElementDto.getYearOfPublish())
                .numOfPublish(printedElementDto.getNumOfPublish()).publishCompanyId(printedElementDto.getPublishCompanyId())
                .publishCompanyName(printedElementDto.getPublishCompanyName()).authors(authors).build();
    }
}

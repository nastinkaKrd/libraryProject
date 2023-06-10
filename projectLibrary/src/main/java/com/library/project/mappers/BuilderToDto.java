package com.library.project.mappers;

import com.library.project.dto.*;
import com.library.project.models.*;
import java.util.List;

public class BuilderToDto {
    public static AuthorDto toDto(Author author){
        return AuthorDto.builder().authorId(author.getAuthorId())
                .name(author.getName()).build();
    }

    public static MovementDto toDto(Movement movement){
        return MovementDto.builder()
                .moveId(movement.getMoveId())
                .status(movement.getStatus())
                .date(movement.getDate())
                .reader(toDto(movement.getReader()))
                .printedElement(toDto(movement.getPrintedElement())).build();
    }

    public static PrintedElementDto toDto(PrintedElement printedElement){
        return PrintedElementDto.builder().elementId(printedElement.getElementId())
                .title(printedElement.getTitle())
                .type(printedElement.getType())
                .style(printedElement.getStyle())
                .amountOfElements(printedElement.getAmountOfElements())
                .yearOfPublish(printedElement.getYearOfPublish())
                .numOfPublish(printedElement.getNumOfPublish())
                .publishCompanyId(printedElement.getPublishCompany().getPublishCompanyId())
                .publishCompanyName(printedElement.getPublishCompany().getNameOfCompany()).build();
    }

    public static ElementJoinAuthorDto toDto(ElementJoinAuthor elementJoinAuthor){
        return ElementJoinAuthorDto.builder()
                .joinId(elementJoinAuthor.getJoinId())
                .elementId(elementJoinAuthor.getPrintedElementId().getElementId())
                .title(elementJoinAuthor.getPrintedElementId().getTitle()).type(elementJoinAuthor.getPrintedElementId().getType())
                .style(elementJoinAuthor.getPrintedElementId().getStyle()).amountOfElements(elementJoinAuthor.getPrintedElementId().getAmountOfElements())
                .yearOfPublish(elementJoinAuthor.getPrintedElementId().getYearOfPublish())
                .numOfPublish(elementJoinAuthor.getPrintedElementId().getNumOfPublish())
                .publishCompanyId(elementJoinAuthor.getPrintedElementId().getPublishCompany().getPublishCompanyId())
                .publishCompanyName(elementJoinAuthor.getPrintedElementId().getPublishCompany().getNameOfCompany())
                .authorId(elementJoinAuthor.getAuthorId().getAuthorId()).authorName(elementJoinAuthor.getAuthorId().getName()).build();
    }

    public static PublishCompanyDto toDto(PublishCompany publishCompany){
        return PublishCompanyDto.builder()
                .publishCompanyId(publishCompany.getPublishCompanyId())
                .nameOfCompany(publishCompany.getNameOfCompany()).build();
    }

    public static ReaderDto toDto(Reader reader){
        return ReaderDto.builder()
                .readerId(reader.getReaderId())
                .name(reader.getName()).build();
    }

    public static AllDataOfElementDto toDto(PrintedElementDto printedElementDto, List<AuthorDto> authors){
        return AllDataOfElementDto.builder().elementId(printedElementDto.getElementId())
                .title(printedElementDto.getTitle())
                .type(printedElementDto.getType())
                .style(printedElementDto.getStyle()).amountOfElements(printedElementDto.getAmountOfElements())
                .yearOfPublish(printedElementDto.getYearOfPublish())
                .numOfPublish(printedElementDto.getNumOfPublish()).publishCompanyId(printedElementDto.getPublishCompanyId())
                .publishCompanyName(printedElementDto.getPublishCompanyName()).authors(authors).build();
    }

    public static ElementDataDto toDto(PrintedElement printedElement, PublishCompany publishCompany, Author author){
        return ElementDataDto.builder().elementId(printedElement.getElementId())
                .title(printedElement.getTitle())
                .style(printedElement.getStyle())
                .type(printedElement.getType())
                .amountOfElements(printedElement.getAmountOfElements())
                .yearOfPublish(printedElement.getYearOfPublish())
                .numOfPublish(printedElement.getNumOfPublish())
                .authorId(author.getAuthorId())
                .authorName(author.getName())
                .publishCompanyId(publishCompany.getPublishCompanyId())
                .publishCompanyName(publishCompany.getNameOfCompany()).build();
    }

    public static UserDto toDto(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .active(user.isActive()).build();
    }

    public static TokenDto toDto(Token token){
        return TokenDto.builder()
                .tokenId(token.getTokenId())
                .value(token.getValue())
                .expired(token.isExpired())
                .revoked(token.isRevoked())
                .user(token.getUser()).build();}
}

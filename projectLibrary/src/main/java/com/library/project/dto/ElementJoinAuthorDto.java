package com.library.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ElementJoinAuthorDto {
    @NotBlank
    private int joinId;
    @NotBlank
    private int elementId;
    @NotBlank
    private String title;
    @NotBlank
    private String type;
    @NotBlank
    private String style;
    @NotBlank
    private int amountOfElements;
    @NotBlank
    private int yearOfPublish;
    @NotBlank
    private int numOfPublish;
    @NotBlank
    private int publishCompanyId;
    @NotBlank
    private String publishCompanyName;
    @NotBlank
    private int authorId;
    @NotBlank
    private String authorName;
}

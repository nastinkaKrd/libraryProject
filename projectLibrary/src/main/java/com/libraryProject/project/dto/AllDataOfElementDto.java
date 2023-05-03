package com.libraryProject.project.dto;

import com.libraryProject.project.models.Author;
import com.libraryProject.project.models.Movement;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllDataOfElementDto {
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
    private List<AuthorDto> authors;


}

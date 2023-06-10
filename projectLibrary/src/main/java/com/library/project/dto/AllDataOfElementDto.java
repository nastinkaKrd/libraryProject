package com.library.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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
    @NotNull
    private List<AuthorDto> authors;
}

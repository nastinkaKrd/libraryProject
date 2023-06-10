package com.library.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class PrintedElementDto {
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
}

package com.libraryProject.project.dto;

import com.libraryProject.project.models.PublishCompany;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

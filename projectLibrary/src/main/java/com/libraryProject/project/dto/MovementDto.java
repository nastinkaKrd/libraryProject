package com.libraryProject.project.dto;

import com.libraryProject.project.models.PrintedElement;
import com.libraryProject.project.models.Reader;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MovementDto {
    @NotBlank
    private String status;
    @NotBlank
    private Date date;

}

package com.library.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private int moveId;
    @NotBlank
    private String status;
    @NotBlank
    private Date date;
    @NotNull
    private ReaderDto reader;
    @NotNull
    private PrintedElementDto printedElement;

}

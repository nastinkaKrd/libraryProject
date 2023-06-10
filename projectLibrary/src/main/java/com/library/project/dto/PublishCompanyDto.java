package com.library.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishCompanyDto {
    @NotBlank
    private int publishCompanyId;
    @NotBlank
    private String nameOfCompany;
}

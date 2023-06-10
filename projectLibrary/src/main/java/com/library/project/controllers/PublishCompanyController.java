package com.library.project.controllers;
import com.library.project.dto.PublishCompanyDto;
import com.library.project.services.PublishCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/companies")
public class PublishCompanyController {
    PublishCompanyService publishCompanyService;
    @Operation(summary = "Get all publish companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found publish companies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PublishCompanyDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PublishCompanyDto> getCompanies(){
        return publishCompanyService.getCompanies();
    }
}

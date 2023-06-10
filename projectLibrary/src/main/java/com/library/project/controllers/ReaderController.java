package com.library.project.controllers;
import com.library.project.dto.ReaderDto;
import com.library.project.dto.PrintedElementDto;
import com.library.project.services.ReaderServiceImplements;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/readers")
public class ReaderController {
    ReaderServiceImplements readerService;
    @Operation(summary = "Get all readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found readers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReaderDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ReaderDto> getReaders(){
        return readerService.getReaders();
    }

    @Operation(summary = "Get all elements that reader read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found elements",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PrintedElementDto.class))}),
            @ApiResponse(responseCode = "404", description = "Reader is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @GetMapping("/elements/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PrintedElementDto> getElements(@PathVariable int id){
        return readerService.getElements(id);
    }

    @Operation(summary = "Add reader to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reader is added"),
            @ApiResponse(responseCode = "208", description = "Reader was registered before", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addReader(@RequestParam(name = "name") String name){
        readerService.addReader(name);
    }

    @Operation(summary = "Delete reader from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reader is deleted"),
            @ApiResponse(responseCode = "404", description = "Reader is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReader(@PathVariable int id){
        readerService.deleteReader(id);
    }
}

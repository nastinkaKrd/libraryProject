package com.library.project.controllers;
import com.library.project.dto.AllDataOfElementDto;
import com.library.project.dto.ElementDataDto;
import com.library.project.dto.MovementDto;
import com.library.project.services.PrintedElementServiceImplements;
import com.library.project.dto.PrintedElementDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/elements")
public class ElementController {
    private PrintedElementServiceImplements printedElementService;

    @Operation(summary = "Get all information about element founded by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found data",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllDataOfElementDto.class))}),
            @ApiResponse(responseCode = "404", description = "Element is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AllDataOfElementDto getElement(@PathVariable int id){
        return printedElementService.getElementInformation(id);
    }


    @Operation(summary = "Get all elements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found elements",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllDataOfElementDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PrintedElementDto>  getElements(){
        return printedElementService.getElements();
    }


    @Operation(summary = "Delete element from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Element is deleted"),
            @ApiResponse(responseCode = "404", description = "Element is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElement(@PathVariable int id){
         printedElementService.deleteElement(id);
    }


    @Operation(summary = "Get all elements founded by style")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found elements",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllDataOfElementDto.class))}),
            @ApiResponse(responseCode = "404", description = "There are nothing elements with this filter", content = @Content)
    })
    @GetMapping("/style/{style}")
    @ResponseStatus(HttpStatus.OK)
    public List<PrintedElementDto> getElementsByStyle(@PathVariable String style){
        return printedElementService.getElementsByStyle(style);
    }

    @Operation(summary = "Get all elements founded by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found elements",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllDataOfElementDto.class))}),
            @ApiResponse(responseCode = "404", description = "There are nothing elements with this filter", content = @Content)
    })
    @GetMapping("/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<PrintedElementDto> getElementsByType(@PathVariable String type){
        return printedElementService.getElementsByType(type);
    }


    @Operation(summary = "Get status and date when element was took or returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found data",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovementDto.class))}),
            @ApiResponse(responseCode = "404", description = "Element is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @GetMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovementDto> showStatusAndDate(@PathVariable int id){
        return printedElementService.getStatusAndDate(id);
    }


    @Operation(summary = "Change elements' status and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found data",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovementDto.class))}),
            @ApiResponse(responseCode = "404", description = "Element or reader is not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Status couldn't be changed", content = @Content),
            @ApiResponse(responseCode = "400", description = "You can't take this book/magazine, there aren't enough books/magazines", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @PutMapping("/change_status")
    @ResponseStatus(HttpStatus.OK)
    public MovementDto changeStatusAndDate(@RequestParam(name = "elementId") int elementId, @RequestParam(name = "status") String status,
                                               @RequestParam(name = "readerId") int readerId){
        return printedElementService.changeStatus(elementId, status, readerId);
    }

    @Operation(summary = "Add element to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element is created"),
            @ApiResponse(responseCode = "208", description = "Element was added before, not now", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right methods' arguments type", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addElement(@RequestBody ElementDataDto printedElement){
        printedElementService.addElement(printedElement);
    }


    @Operation(summary = "Get all styles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found styles",
                    content = { @Content(mediaType = "String",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/styles")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getStyles(){
        return printedElementService.showStyles();
    }

    @Operation(summary = "Get all types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found types",
                    content = { @Content(mediaType = "String",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getTypes(){
        return printedElementService.showTypes();
    }

}

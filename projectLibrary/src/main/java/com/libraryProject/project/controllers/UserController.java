package com.libraryProject.project.controllers;
import com.libraryProject.project.dto.UserDto;
import com.libraryProject.project.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    UserService userService;

    @Operation(summary = "Log in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged in", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Not right email", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right password", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right method's arguments type", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not found", content = @Content)
    })
    @PutMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDto logIn(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "id") int id){
        return userService.logIn(email, password, id);
    }

    @Operation(summary = "Log out user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged out", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Not right email", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right password", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not right method's arguments type", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not found", content = @Content)
    })
    @PutMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public UserDto logOut(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "id") int id){
        return userService.logOut(email, password, id);
    }



}

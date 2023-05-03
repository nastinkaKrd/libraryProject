package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.AuthorDto;
import com.libraryProject.project.models.Author;
import com.libraryProject.project.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImplements implements AuthorService{
    AuthorRepository authorRepository;
    @Override
    public List<AuthorDto> showAuthors(){
        List<Author> listOfAuthors = authorRepository.findAll();
        List<AuthorDto> listDto = new ArrayList<>();
        for (Author temp: listOfAuthors){
            listDto.add(BuilderToDto.toDto(temp));
        }
        return listDto;
    }

}

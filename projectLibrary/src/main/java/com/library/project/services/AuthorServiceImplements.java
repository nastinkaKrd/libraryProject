package com.library.project.services;

import com.library.project.dto.AuthorDto;
import com.library.project.mappers.BuilderToDto;
import com.library.project.repositories.AuthorRepository;
import com.library.project.mappers.BuilderToModel;
import com.library.project.models.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImplements implements AuthorService{
    AuthorRepository authorRepository;
    @Override
    public List<AuthorDto> showAuthors(){
        List<Author> listOfAuthors = authorRepository.findAll();
        List<AuthorDto> listDto = new ArrayList<>();
        listOfAuthors.forEach(author -> listDto.add(BuilderToDto.toDto(author)));
        return listDto;
    }

    @Override
    public Optional<Author> getById(int id) {
        return authorRepository.findById(id);
    }

    @Override
    public void saveAuthor(AuthorDto authorDto) {
        authorRepository.save(BuilderToModel.toModel(authorDto));
    }

}

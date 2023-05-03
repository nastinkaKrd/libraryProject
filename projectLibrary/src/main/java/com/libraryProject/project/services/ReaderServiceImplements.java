package com.libraryProject.project.services;

import com.libraryProject.project.controllers.BuilderToDto;
import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.dto.ReaderDto;
import com.libraryProject.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.libraryProject.project.exceptions.ApiRequestExceptionNotFound;
import com.libraryProject.project.models.Movement;
import com.libraryProject.project.models.Reader;
import com.libraryProject.project.repositories.MovementRepository;
import com.libraryProject.project.repositories.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@AllArgsConstructor
public class ReaderServiceImplements implements ReaderService{
    ReaderRepository readerRepository;
    MovementRepository movementRepository;
    @Override
    public List<ReaderDto> getReaders(){
        List<Reader> listOfReaders = readerRepository.findAll();
        List<ReaderDto> readerDtos = new ArrayList<>();
        for (Reader temp: listOfReaders){
            readerDtos.add(BuilderToDto.toDto(temp));
        }
        return readerDtos;
    }

    @Override
    public List<PrintedElementDto> getElements(int id){
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()){
            List<Movement> listOfElements = movementRepository.findAllByReaderId(reader.get());
            List<PrintedElementDto> elementDtos = new ArrayList<>();
            for (Movement temp: listOfElements){
                elementDtos.add(BuilderToDto.toDto(temp.getPrintedElementId()));
            }
            return elementDtos.stream().distinct().toList();
        }else {
            throw new ApiRequestExceptionNotFound("Reader is not found");
        }
    }

    @Override
    public void addReader(String name, int id){
        if (readerRepository.findById(id).isEmpty()) {
            readerRepository.save(new Reader(id, name));
        }
        else {
            throw new ApiRequestExceptionAlreadyReported("Reader was registered before");
        }
    }

    @Override
    public void deleteReader(int id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            List<Movement> listOfId = movementRepository.findAllByReaderId(reader.get());
            if (!listOfId.isEmpty()){
                for (Movement temp: listOfId){
                    movementRepository.deleteById(temp.getMoveId());
                }
            }
            readerRepository.delete(reader.get());
        }
        else {
            throw new ApiRequestExceptionNotFound("Reader is not found");
        }
    }
}

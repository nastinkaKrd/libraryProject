package com.library.project.services;

import com.library.project.dto.MovementDto;
import com.library.project.dto.ReaderDto;
import com.library.project.exceptions.ApiRequestExceptionAlreadyReported;
import com.library.project.exceptions.ApiRequestExceptionNotFound;
import com.library.project.mappers.BuilderToDto;
import com.library.project.models.Reader;
import com.library.project.repositories.ReaderRepository;
import com.library.project.dto.PrintedElementDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@AllArgsConstructor
public class ReaderServiceImplements implements ReaderService{
    ReaderRepository readerRepository;
    MovementService movementService;

    @Override
    public Optional<Reader> getById(int id) {
        return readerRepository.findById(id);
    }

    @Override
    public List<ReaderDto> getReaders(){
        List<Reader> listOfReaders = readerRepository.findAll();
        List<ReaderDto> readerDtos = new ArrayList<>();
        listOfReaders.forEach(reader -> readerDtos.add(BuilderToDto.toDto(reader)));
        return readerDtos;
    }

    @Override
    public List<PrintedElementDto> getElements(int id){
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()){
            List<MovementDto> listOfElements = movementService.getDataByReader(BuilderToDto.toDto(reader.get()));
            List<PrintedElementDto> elementDtos = new ArrayList<>();
            listOfElements.forEach(movementDto -> elementDtos.add(movementDto.getPrintedElement()));
            return elementDtos.stream().distinct().toList();
        }else {
            throw new ApiRequestExceptionNotFound("Reader is not found");
        }
    }

    @Override
    public void addReader(String name){
        if (readerRepository.findByName(name).isEmpty()) {
            List<Reader> readers = readerRepository.findAll();
            int readerId;// = readers.get(readers.size()-1).getReaderId() + 1;
            if(readers.isEmpty()){
                readerId = 1;
            }else {
                readerId = readers.get(readers.size()-1).getReaderId() + 1;
            }
            readerRepository.save(new Reader(readerId, name));
        }
        else {
            throw new ApiRequestExceptionAlreadyReported("Reader was registered before");
        }
    }

    @Override
    public void deleteReader(int id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            List<MovementDto> listOfId = movementService.getDataByReader(BuilderToDto.toDto(reader.get()));
            if (!listOfId.isEmpty()){
                for (MovementDto temp: listOfId){
                    movementService.deleteById(temp.getMoveId());
                }
            }
            readerRepository.delete(reader.get());
        }
        else {
            throw new ApiRequestExceptionNotFound("Reader is not found");
        }
    }
}

package com.libraryProject.project.webControllers;

import com.libraryProject.project.dto.PrintedElementDto;
import com.libraryProject.project.dto.ReaderDto;
import com.libraryProject.project.services.ReaderServiceImplements;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/reader")
public class ReaderWebController {
    ReaderServiceImplements readerService;
    @GetMapping("/readers")
    public String getReaders(Model model) {
        model.addAttribute("readers", readerService.getReaders());
        return "reader/readers";
    }

    @GetMapping("/reader_elements")
    public String reader_elements(){
        return "reader/reader_elements";
    }

    @GetMapping("/get_elements")
    public String getElementss(@RequestParam(name = "id") int id, Model model) {
        List<PrintedElementDto> elementDtos = readerService.getElements(id);
        if (elementDtos.isEmpty()){
            model.addAttribute("message", "Nothing read (new reader)");
        }
        model.addAttribute("reader_elements", elementDtos);
        return "reader/get_elements";
    }
    @GetMapping("/add_reader")
    public String addNewReaderWeb(){
        return "reader/add_reader";
    }
    @PostMapping("/add_reader/add")
    public String addReaderWeb(@RequestParam(name = "readerId") int id, @RequestParam(name = "name") String name){
        try {
            readerService.addReader(name, id);
            return "redirect:../../hello";
        }catch (Exception exception){
            return "redirect:../../errorPage";
        }
    }

    @GetMapping("/delete_reader")
    public String deleteNewReaderWeb(){
        return "reader/delete_reader";
    }
    @PostMapping("/delete_reader/deleted")
    public String deleteReaderWeb(@RequestParam(name = "id") int id){
        try {
            readerService.deleteReader(id);
            return "redirect:../../hello";
        }catch (Exception exception){
            return "redirect:../../errorPage";
        }
    }
}

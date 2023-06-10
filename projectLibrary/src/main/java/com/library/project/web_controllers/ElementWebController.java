package com.library.project.web_controllers;

import com.library.project.dto.ElementDataDto;
import com.library.project.mappers.BuilderToDto;
import com.library.project.models.Author;
import com.library.project.models.PrintedElement;
import com.library.project.models.PublishCompany;
import com.library.project.services.PrintedElementServiceImplements;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/elementsWeb")
public class ElementWebController {
    private PrintedElementServiceImplements printedElementService;

    @GetMapping("/element_id")
    public String returnFormPageOfElement(){
        return "elementsWeb/element_id";
    }

    @GetMapping("/show_element")
    public String getElementData(@RequestParam(name = "id") int id, Model model){
         model.addAttribute("element", printedElementService.getElementInformation(id));
         return "elementsWeb/show_element";
    }

    @GetMapping("/show_elements")
    public String showElements(Model model){
        model.addAttribute("elements", printedElementService.getElements());
        return "elementsWeb/show_elements";
    }

    @GetMapping("/delete_element")
    public String deleteNewElementWeb(){
        return "elementsWeb/delete_element";
    }

    @PostMapping("/delete_element/deleted")
    public String deleteElement(@RequestParam(name = "id") int id){
        printedElementService.deleteElement(id);
        return "redirect:/hello";
    }

    @GetMapping("/style")
    public String getElByStyle(@RequestParam(name = "style") String style, Model model) {
        model.addAttribute("elements", printedElementService.getElementsByStyle(style));
        return "elementsWeb/style";
    }

    @GetMapping("/type")
    public String getElByType(@RequestParam(name = "type") String type, Model model){
        model.addAttribute("elements", printedElementService.getElementsByType(type));
        return "elementsWeb/type";
    }

    @GetMapping("/show_status")
    public String showStatus(){
        return "elementsWeb/show_status";
    }

    @GetMapping("/status_data")
    public String showStatusAndDate(@RequestParam(name = "id") int elementId, Model model){
        model.addAttribute("data", printedElementService.getStatusAndDate(elementId));
        return "elementsWeb/status_data";
    }

    @GetMapping("/change_status")
    public String changeElementStatusWeb(){
        return "elementsWeb/change_status";
    }

    @PostMapping("/status/changed")
    public String changeStatusAndDate(@RequestParam(name = "id") int elementId, @RequestParam(name = "status") String status,
                                      @RequestParam(name = "reader_id") int readerId){
        printedElementService.changeStatus(elementId, status, readerId);
        return "redirect:/hello";
    }

    @GetMapping("/add_element")
    public String addNewElementWeb(){
        return "elementsWeb/add_element";
    }

    @PostMapping("/add_element/add")
    public String addElement(@RequestParam(name = "id") int id, @RequestParam(name = "title") String title, @RequestParam(name = "type") String type
            , @RequestParam(name = "style") String style, @RequestParam(name = "amount") int amount, @RequestParam(name = "year") int year
            , @RequestParam(name = "num") int num, @RequestParam(name = "publish_id") int publishId, @RequestParam(name = "company_name") String companyName
            , @RequestParam(name = "author_id") int authorId, @RequestParam(name = "author_name") String authorName){
        PublishCompany publishCompany = new PublishCompany(publishId, companyName);
        PrintedElement printedElement = new PrintedElement(id, title, type, style, amount, year, num, publishCompany);
        Author author = new Author(authorId, authorName);
        ElementDataDto elementDataDto = BuilderToDto.toDto(printedElement, publishCompany, author);
        printedElementService.addElement(elementDataDto);
        return "redirect:/hello";
    }
}

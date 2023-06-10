package com.library.project.web_controllers;

import com.library.project.dto.AuthorDto;
import com.library.project.services.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class AuthorWebController {
    AuthorService authorService;
    @GetMapping("/author/authors")
    public String getAuthorsWeb(Model model)  {
        List<AuthorDto> authorDtoList = authorService.showAuthors();
        model.addAttribute("authors", authorDtoList);
        return "author/authors";
    }
}

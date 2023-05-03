package com.libraryProject.project.webControllers;

import com.libraryProject.project.dto.AuthorDto;
import com.libraryProject.project.services.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
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

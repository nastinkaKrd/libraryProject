package com.library.project.web_controllers;

import com.library.project.services.PublishCompanyServiceImplements;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
@Slf4j
public class PublishCompanyWebController {
    PublishCompanyServiceImplements publishCompanyService;
    @GetMapping("/company/companies")
    public String getCompaniesWeb(Model model) {
        model.addAttribute("companies", publishCompanyService.getCompanies());
        return "company/companies";
    }
}

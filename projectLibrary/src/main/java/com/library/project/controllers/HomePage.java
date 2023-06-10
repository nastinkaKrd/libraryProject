package com.library.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {
    @GetMapping("/hello")
    public String getHello(){
        return "/hello";
    }
    @GetMapping("/errorPage")
    public String getError(){
        return "/errorPage";
    }
}

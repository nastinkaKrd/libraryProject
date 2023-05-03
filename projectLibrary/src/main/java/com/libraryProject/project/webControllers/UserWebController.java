package com.libraryProject.project.webControllers;

import com.libraryProject.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/log")
@AllArgsConstructor
public class UserWebController {
    UserService userService;

    @GetMapping("/new_log_in")
    public String logInNew(){
        return "log/new_log_in";
    }
    @PostMapping("/new_log_in/log")
    public String logInWeb(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "id") int id){
        try {
            userService.logIn(email, password, id);
            return "redirect:../../hello";
        }catch (Exception exception){
            return "redirect:../../errorPage";
        }
    }

    @GetMapping("/new_log_out")
    public String logOutNew(){
        return "log/new_log_out";
    }
    @PostMapping("/new_log_out/log")
    public String logOutWeb(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "id") int id){
        try {
            userService.logOut(email, password, id);
            return "redirect:../../hello";
        }catch (Exception exception){
            return "redirect:../../errorPage";
        }
    }


}

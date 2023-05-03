package com.libraryProject.project.scheduled;

import com.libraryProject.project.models.User;
import com.libraryProject.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@EnableScheduling
public class UserLoggedOut {
    UserRepository userRepository;
    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void logOut(){
        List<User> users = userRepository.findAll();
        for(User temp: users){
            if (temp.isLogged()){
                userRepository.save(new User(temp.getUserId(), temp.getUserName(), temp.getEmail(), temp.getPassword(), false));
            }
        }
    }
}

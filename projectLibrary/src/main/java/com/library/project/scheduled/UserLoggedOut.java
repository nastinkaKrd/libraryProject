package com.library.project.scheduled;

import com.library.project.models.User;
import com.library.project.repositories.UserRepository;
import com.library.project.repositories.TokenRepository;
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
    TokenRepository tokenRepository;
    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void logOut(){
        List<User> users = userRepository.findAll();
        for(User temp: users){
            if (temp.isActive()){
                temp.setActive(false);
                userRepository.save(temp);
            }
        }
        tokenRepository.deleteAll();

    }
}

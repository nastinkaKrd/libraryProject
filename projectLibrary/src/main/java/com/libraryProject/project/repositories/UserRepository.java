package com.libraryProject.project.repositories;

import com.libraryProject.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    //@Query("update User u set u.isLogged=false")
    //@Modifying
    //void logOutAllUsers();
}

package com.library.project.repositories;

import com.library.project.models.Token;
import com.library.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByUser(User user);

    Optional<Token> findByValue(String jwt);
}

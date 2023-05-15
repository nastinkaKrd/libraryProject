package com.libraryProject.project.repositories;

import com.libraryProject.project.models.Token;
import com.libraryProject.project.models.User;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    /*@Query(value = """
      select t from tokens t inner join users u on t.user = u.user_id where u.user_id = :id and (t.expired = false or t.revoked = false)""")
    List<Token> findAllValidTokenByUser(Integer id);*/
    List<Token> findByUser(User user);

    Optional<Token> findByValue(String jwt);
}

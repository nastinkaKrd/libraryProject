package com.libraryProject.project.repositories;

import com.libraryProject.project.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    void itShouldFindByEmail() {
        User user = new User(1, "name1", "qwerty@qwe.com", "password", true);
        userRepository.save(user);
        User userData = userRepository.findByEmail("qwerty@qwe.com").get();
        assert(userData).equals(user);
    }
}
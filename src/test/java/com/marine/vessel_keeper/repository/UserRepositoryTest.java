package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.user.Role;
import com.marine.vessel_keeper.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TestEntityManager entityManager;
    private final String login = "TestLogin";
    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setLogin(login);
    }
    @Test
    void addUser(){
        User savedUser = repository.save(user);
        User reviewedUser = entityManager.find(User.class, savedUser.getId());
        assertEquals(savedUser.getLogin(), reviewedUser.getLogin());
    }
    @Test
    void removeUser(){
        User savedUser = repository.save(user);
        repository.deleteById(savedUser.getId());
        assertNull(entityManager.find(User.class, savedUser.getId()));
    }
    @Test
    void findUserByLogin() {
        User savedUser = repository.save(user);
        User reviewedUser = repository.findUserByLogin(user.getLogin()).orElseThrow();
        assertEquals(savedUser.getId(), reviewedUser.getId());
    }
}
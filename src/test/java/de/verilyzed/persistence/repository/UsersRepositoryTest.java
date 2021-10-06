package de.verilyzed.persistence.repository;

import co.aikar.idb.DB;
import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.persistence.model.User;
import de.verilyzed.service.UserService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersRepositoryTest {


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMoneyForUsername() throws MoneyFetchException {
        UserService.setUsersRepository(new UsersRepository());
try (MockedStatic<DB> utils = Mockito.mockStatic(DB.class)) {
    utils.when(() -> DB.getFirstColumn("SELECT money FROM users WHERE name= ?", "test")).thenReturn(100);
    assertEquals(UserService.getMoney("test"), 100);
}
    }

    @Test
    void getUserbyUUID() {

    }

    @Test
    void getUserbyName() {

    }

    @Test
    void insertUser() {

    }

    @Test
    void updateMoneyForUsername() {
    }

    @Test
    void exchangeMoney() {
    }

    @Test
    void userExists() {
    }

    @Test
    void updateBackpack() {
    }

    @Test
    void getUuid() {
    }
}
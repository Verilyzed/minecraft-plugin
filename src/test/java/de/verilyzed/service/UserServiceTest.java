package de.verilyzed.service;


import co.aikar.idb.DB;
import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.persistence.model.User;
import de.verilyzed.persistence.repository.UsersRepository;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    UsersRepository userrepo;
    @BeforeEach
    void setUp() {
        userrepo = mock(UsersRepository.class);
        UserService.setUsersRepository(userrepo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMoney() throws MoneyFetchException {
        when(userrepo.getMoneyForUsername("test")).thenReturn(100);
        UserService.setUsersRepository(userrepo);
        assertEquals(UserService.getMoney("test"), 100);
        when(userrepo.getMoneyForUsername("test")).thenThrow(new MoneyFetchException(""));
        assertThrows(MoneyFetchException.class, () -> UserService.getMoney("test"));
    }

    @Test
    void setMoney() {
    }

    @Test
    void sendMoney() {}

    @Test
    void getBackpack() {

    }

    @Test
    void setBackpack() {
    }

    @Test
    void ensureUserExists() {
    }

    @Test
    void getUserByName() {
        when(userrepo.getUserbyName("test")).thenReturn(new User("test", "uuid", 100, new JSONObject()));
        assertEquals(userrepo.getUserbyName("test").getUsername(), "test");
        assertEquals(userrepo.getUserbyName("test").getUuid(), "uuid");
        assertEquals(userrepo.getUserbyName("test").getMoney(), 100);
    }

    @Test
    void getUuidByName() {
        when(userrepo.getUuid("name")).thenReturn("uuid");
        assertEquals(UserService.getUuidByName("name"), "uuid");
    }

    @Test
    void setUsersRepository() {
    }
}
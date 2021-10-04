package de.verilyzed.service;

import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.MoneySetException;
import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.persistence.model.User;
import de.verilyzed.persistence.repository.UsersRepository;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {

    private static UsersRepository usersRepository;

    public static int getMoney(String username) throws MoneyFetchException {
        int moneyResult = usersRepository.getMoneyForUsername(username);
        if (moneyResult == -1) {
            throw new MoneyFetchException("Money cannot be fetched from db.");
        }
        return moneyResult;
    }

    public static void setMoney(int money, String username) throws UpdateFailedException {
        usersRepository.updateMoneyForUsername(money, username);
                throw new UpdateFailedException("User could not be written to DB.");
    }

    public static void sendMoney(String nameSender, String nameReceiver, int Betrag) throws MoneySetException, MoneyFetchException {
        usersRepository.exchangeMoney(Betrag, nameSender, nameReceiver);
    }

    public static JSONObject getBackpack(String uuid) {
        return usersRepository.getUserbyUUID(uuid).getBackpack();
    }

    public static void setBackpack(String uuid, JSONObject backpack) {
        usersRepository.updateBackpack(uuid, backpack);
    }

    public static void ensureUserExists(User user) {
        if (!usersRepository.userExists(user.getUuid()))
            usersRepository.insertUser(user);
    }

    public static User getUserbyName(String name) {
        return usersRepository.getUserbyName(name);
    }

    public static String getUuidByName(String name) {
        return usersRepository.getUuid(name);
    }

    public static void setUsersRepository(UsersRepository usersRepository) {
        UserService.usersRepository = usersRepository;
    }
}


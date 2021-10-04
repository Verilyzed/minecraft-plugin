package de.verilyzed.service;

import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.MoneySetException;
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

    public static boolean setMoneyByName(int money, String username) {
        return usersRepository.updateMoneyForUsername(money, username);
    }

    public static void sendMoney(String nameSender, String nameReceiver, int Betrag) throws MoneySetException, MoneyFetchException {
        usersRepository.exchangeMoney(Betrag, nameSender, nameReceiver);
        if (false) {
            int moneySender = getMoney(nameSender);
            int moneyReceiver = getMoney(nameReceiver);
            if (Betrag > 0) {
                moneySender -= Betrag;
                moneyReceiver += Betrag;
            } else {
                return;
            }
            boolean res1 = usersRepository.updateMoneyForUsername(moneySender, nameSender);
            boolean res2 = usersRepository.updateMoneyForUsername(moneyReceiver, nameReceiver);
            if (res1 && res2) {
                return;
            }
            throw new MoneySetException("Money cannot be set");
        }
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

    public static User getUserByName(String name) {
        return usersRepository.getUserbyName(name);
    }

    public static String getUuidByName(String name) {
        return usersRepository.getUuid(name);
    }

    public static void setUsersRepository(UsersRepository usersRepository) {
        UserService.usersRepository = usersRepository;
    }
}


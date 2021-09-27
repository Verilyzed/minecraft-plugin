package de.verilyzed.service;

import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.persistence.model.User;
import de.verilyzed.persistence.repository.UsersRepository;
import org.json.simple.JSONArray;
import java.sql.SQLException;

public class UserService {

    private static UsersRepository usersRepository;
    public static int getMoney(String username) throws MoneyFetchException {
        int moneyResult = usersRepository.getMoneyForUsername(username);
        if (moneyResult == -1) {
            throw new MoneyFetchException("Money cannot be fetched from db.");
        }
        return moneyResult;
    }

    public static boolean sendMoney(String nameSender, String nameReceiver, int Betrag) throws SQLException {
        try {
            int moneySender = getMoney(nameSender);
            int moneyReceiver = getMoney(nameReceiver);
            if (Betrag > 0) {
                moneySender -= Betrag;
                moneyReceiver += Betrag;
            } else {
                return false;
            }
            boolean res1 = usersRepository.updateMoneyForUsername(moneySender, nameSender);
            boolean res2 = usersRepository.updateMoneyForUsername(moneyReceiver, nameReceiver);
            if (res1 && res2)
                return true;
            throw new SQLException("Money cannot be set");
        } catch (MoneyFetchException e) {
            return false;
        }
    }
    public static JSONArray getBackpack(String uuid) {
        return usersRepository.getUser(uuid).getBackpack();
    }
    public static void setBackpack(String uuid, JSONArray backpack) {
        usersRepository.updateBackpack(uuid, backpack);
    }
    public static void ensureUserExists(User user) {
        if (!usersRepository.userExists(user.getUuid()))
            usersRepository.insertUser(user);
    }
}


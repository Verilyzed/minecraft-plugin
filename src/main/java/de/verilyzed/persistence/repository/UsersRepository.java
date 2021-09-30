package de.verilyzed.persistence.repository;

import co.aikar.idb.DB;
import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.User;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class UsersRepository {

    public int getMoneyForUsername(String username) throws MoneyFetchException {
        int ret = -1;
        try {
            ret = DB.getFirstColumn("SELECT money FROM users WHERE name= ?", username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MoneyFetchException("money cannot be fetched from DB");
        }
        return ret;
    }
    public User getUserbyUUID(String uuid) {
        User user = null;
        try {
            user = new User(DB.getFirstRow("SELECT * FROM users WHERE uuid=?", uuid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public User getUserbyName(String name) {
        User user = null;
        try {
            user = new User(DB.getFirstRow("SELECT * FROM users WHERE name=?", name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean insertUser(User user) {
        DB.createTransactionAsync(stm -> {
            if (!stm.inTransaction()) {
                throw new IllegalStateException("Currency Operations require a transaction");
            }
            try {
                return stm.executeUpdateQuery("INSERT INTO users (uuid, name, money, backpack) " +
                        "VALUES (?, ?, ?, ?)", user.getUuid(), user.getUsername(), user.getMoney(), user.getBackpack().toJSONString()) > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
        return true;
    }

    public boolean updateMoneyForUsername(int money, String username) {
        DB.executeUpdateAsync("UPDATE users SET money = ? WHERE name = ?", money, username);
        return false;
    }

    public void exchangeMoney(int money, String usernameSender, String usernameReceiver) throws MoneyFetchException {
        int betragSender = getMoneyForUsername(usernameSender) - money;
        int betragReceiver = getMoneyForUsername(usernameReceiver) + money;
        if (betragSender < 0 || money <= 0)
            throw new IllegalStateException("Money has to be positive. A User cannot send more than he has.");
        DB.createTransactionAsync(stm -> {
                    if (!stm.inTransaction()) {
                        throw new IllegalStateException("Currency Operations require a transaction");
                    }
                    try {
                        stm.executeUpdateQuery("UPDATE users SET money = ? WHERE name = ?", betragSender, usernameSender);
                        stm.executeUpdateQuery("UPDATE users SET money = ? WHERE name = ?", betragReceiver, usernameReceiver);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return false;
                },
                () -> KrassAlla.log.log(new LogRecord(Level.INFO, "Es wurde Geld überwiesen.")),
                () -> KrassAlla.log.log(new LogRecord(Level.INFO, "Money could not be transferred. No Records have changed"))
        );
    }

    public boolean userExists(String uuid) {
        try {
            return ((long) DB.getFirstColumn("SELECT COUNT(*) FROM users WHERE uuid = ?", uuid)) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateBackpack(String uuid, JSONObject backpack) {
        DB.executeUpdateAsync("UPDATE users SET backpack = ? WHERE uuid = ?", backpack.toJSONString(), uuid);
    }

    public String getUuid(String name) {
        String uuid = null;

        try {
            uuid = DB.getFirstColumn("SELECT uuid FROM users WHERE name = ?", name).toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uuid;
    }
}

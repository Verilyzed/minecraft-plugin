package de.verilyzed.persistence.repository;

import co.aikar.idb.DB;
import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.User;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class UsersRepository {

    public int getMoneyForUsername(String username) throws MoneyFetchException {
        int ret;
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

    public void insertUser(User user) {
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
    }

    public void updateMoneyForUsername(int money, String username) throws UpdateFailedException {
        CompletableFuture<Integer> ret = DB.executeUpdateAsync("UPDATE users SET money = ? WHERE name = ?", money, username);
        int value=-1;
        try {
            value = ret.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (value != 1) {
            throw new UpdateFailedException("Money was not updated");
        }
    }

    public void exchangeMoney(int money, String usernameSender, String usernameReceiver) throws MoneyFetchException, UpdateFailedException {
        int betragSender = getMoneyForUsername(usernameSender) - money;
        int betragReceiver = getMoneyForUsername(usernameReceiver) + money;
        AtomicBoolean ret = new AtomicBoolean(true);
        if (betragSender < 0 || money <= 0)
            throw new IllegalStateException("Money has to be positive. A User cannot send more than he has.");
        // manual commit and close is not necessary. The createTransactionAsync commits and autocloses depending on return value
        DB.createTransactionAsync(stm -> {
                    if (!stm.inTransaction()) {
                        throw new IllegalStateException("Currency Operations require a transaction");
                    }
                    try {
                        stm.startTransaction();
                        stm.executeUpdateQuery("UPDATE users SET money = ? WHERE name = ?", betragSender, usernameSender);
                        stm.executeUpdateQuery("UPDATE users SET money = ? WHERE name = ?", betragReceiver, usernameReceiver);
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                },
                () -> KrassAlla.log.log(new LogRecord(Level.INFO, "Es wurde Geld überwiesen.")),
                () -> {
                    KrassAlla.log.log(new LogRecord(Level.INFO, "Money could not be transferred. No Records have changed"));
                ret.set(false);
        });
        if(!ret.get())
            throw new UpdateFailedException("No money has been transferred.");
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

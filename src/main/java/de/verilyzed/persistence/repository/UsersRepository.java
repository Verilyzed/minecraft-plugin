package de.verilyzed.persistence.repository;

import co.aikar.idb.DB;
import co.aikar.idb.TransactionCallback;
import de.verilyzed.persistence.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;

public class UsersRepository {

    public int getMoneyForUsername(String username) {
        int ret = -1;
        try {
            ret = DB.getFirstColumn("SELECT money FROM users WHERE name= ?", username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public User getUser(String uuid) {
        User user = null;
        try {
            user = new User(DB.getFirstRow("SELECT * FROM users WHERE uuid=?", uuid));
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

    public boolean userExists(String uuid) {
        try {
            return ((long)DB.getFirstColumn("SELECT COUNT(*) FROM users WHERE uuid = ?", uuid))==1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateBackpack(String uuid, JSONObject backpack) {
        DB.executeUpdateAsync("UPDATE users SET backpack = ? WHERE uuid = ?", backpack.toJSONString(), uuid);
    }
}

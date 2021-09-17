package de.verilyzed.generic;

import co.aikar.idb.DB;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class BusinessLogic {

    public BusinessLogic() {
    }

    public int getMoney(String name) {
        int ret = -1;
        try {
            ret = DB.getFirstColumn("SELECT money FROM users WHERE name= ?", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean sendMoney(String nameSender, String nameReceiver, int Betrag) throws SQLException {
        int moneySender = getMoney(nameSender);
        int moneyReceiver = getMoney(nameReceiver);
        if (moneySender == -1 || moneyReceiver == -1)
            throw new SQLException("Money cannot be fetched from db.");
        if (Betrag > 0) {
            moneySender -= Betrag;
            moneyReceiver += Betrag;
        } else {
            return false;
        }
        boolean res1 = updateEntry("money", Integer.toString(moneySender), "name", nameSender, "users");
        boolean res2 = updateEntry("money", Integer.toString(moneyReceiver), "name", nameReceiver, "users");
        if (res1 && res2)
            return true;
        throw new SQLException("Money cannot be set");
    }

    public boolean insertEntry(String fields, String values, String table) {
        DatabaseHandler db = new DatabaseHandler();
        String abfrage = "INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ");";
        boolean result = db.executeUpdate(abfrage);
        db.close();
        return result;
    }

    public boolean updateEntry(String field, String value, String cond, String condValue, String table) {
        DB.executeUpdateAsync("UPDATE ? SET ? = '?' WHERE ? = '?'", table, field, value, cond, condValue);
        return true;
    }

    public boolean checkUserExistsInDB(UUID uuid) {
        try {
            return (int)DB.getFirstColumn("SELECT COUNT(*) AS c FROM users WHERE uuid = '?'", uuid) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUserinDatabase(@NotNull Player p, @NotNull JSONObject jsonObject) {
        return insertEntry("money, backpack, uuid, name", jsonObject.get("money") + ", '" + jsonObject.get("backpack") + "', '" + p.getUniqueId() + "', '" + p.getName(), "users");
    }

    // Below is legacy code to be compatible with the deprecated JSON-File Storage-Backend we used before.
    public JSONObject getJSONObject(UUID uuid) {
        DatabaseHandler db = new DatabaseHandler();
        String abfrage = "SELECT * FROM users WHERE uuid='" + uuid + "';";
        try (ResultSet rs = db.executeQuery(abfrage)) {
            JSONArray object = mapResultSet(rs);
            db.close();
            return (JSONObject) object.get(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return new JSONObject();
    }

    public boolean setJSONObject(UUID uuid, JSONObject jsonObject) {
        DatabaseHandler db = new DatabaseHandler();
        String abfrage = "UPDATE users SET money=" + jsonObject.get("money") + ", backpack=" + jsonObject.get("backpack") + "WHERE uuid=" + uuid.toString() + ";";

        db.close();
        return db.executeUpdate(abfrage);
    }


    public JSONArray mapResultSet(ResultSet rs) {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            do {
                jsonObject = new JSONObject();
                for (int index = 1; index <= columnCount; index++) {
                    String column = rsmd.getColumnName(index);
                    Object value = rs.getObject(column);
                    if (value == null) {
                        jsonObject.put(column, "");
                    } else if (value instanceof Integer) {
                        jsonObject.put(column, value);
                    } else if (value instanceof String) {
                        jsonObject.put(column, value);
                    } else if (value instanceof Boolean) {
                        jsonObject.put(column, value);
                    } else if (value instanceof Date) {
                        jsonObject.put(column, ((Date) value).getTime());
                    } else if (value instanceof Long) {
                        jsonObject.put(column, value);
                    } else if (value instanceof Double) {
                        jsonObject.put(column, value);
                    } else if (value instanceof Float) {
                        jsonObject.put(column, value);
                    } else {
                        throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
                    }
                }
                jArray.add(jsonObject);
            } while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return jArray;
    }
}
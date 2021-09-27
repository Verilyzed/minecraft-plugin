package de.verilyzed.generic;

import co.aikar.idb.DB;
import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.service.UserService;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Language;
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
        try {
            return UserService.getMoney(name);
        } catch (MoneyFetchException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean sendMoney(String nameSender, String nameReceiver, int Betrag) throws SQLException {
        return UserService.sendMoney(nameSender, nameReceiver, Betrag);
    }

    public boolean updateEntry(String field, String value, String cond, String condValue, String table) {
        DB.executeUpdateAsync("UPDATE ? SET ? = '?' WHERE ? = '?'", table, field, value, cond, condValue);
        return true;
    }

    // Below is legacy code to be compatible with the deprecated JSON-File Storage-Backend we used before.
    public JSONObject getJSONObject(UUID uuid) {
        @Language("SQL") String abfrage = "SELECT * FROM users WHERE uuid='" + uuid + "';";

        JSONArray object;
        try {
            ResultSet rs = (ResultSet) DB.getFirstRow(abfrage);
            object = mapResultSet(rs);
            rs.close();
            return (JSONObject) object.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public boolean setJSONObject(UUID uuid, JSONObject jsonObject) {
        @Language("SQL") String abfrage = "UPDATE users SET money=" + jsonObject.get("money") + ", backpack=" + jsonObject.get("backpack") + "WHERE uuid=" + uuid.toString() + ";";
        try {
            return DB.executeUpdate(abfrage) != 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
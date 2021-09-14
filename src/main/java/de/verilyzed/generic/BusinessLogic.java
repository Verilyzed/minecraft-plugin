package de.verilyzed.generic;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class BusinessLogic {

    public static int getMoney(String name) {
        String abfrage = "SELECT money FROM users WHERE name='" + name + "';";
        DatabaseHandler db = new DatabaseHandler();
        try {
            ResultSet rs = db.executeQuery(abfrage);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean sendMoney(String nameSender, String nameReceiver, int Betrag) {
        int moneySender = getMoney(nameSender);
        int moneyReceiver = getMoney(nameReceiver);
        if (moneySender == -1 || moneyReceiver == -1)
            return false;
        if (Betrag > 0) {
            moneySender -= Betrag;
            moneyReceiver += Betrag;
        } else {
            return false;
        }
        boolean res1 = writeToDB("money", Integer.toString(moneySender), "name", nameSender);
        boolean res2 = writeToDB("money", Integer.toString(moneyReceiver), "name", nameReceiver);
        return (res1 || res2);

    }

    public static boolean writeToDB(String field, String value, String cond, String condValue) {
        String abfrage = "UPDATE users SET '" + field + "'= '" + value + "' WHERE '" + cond + "' =  '" + condValue + "';";
        DatabaseHandler db = new DatabaseHandler();
        boolean res = db.executeUpdate(abfrage);
        return res;
    }

    public boolean checkUserExistsInDB(UUID uuid) {
        DatabaseHandler db = new DatabaseHandler();
        ResultSet rs = db.executeQuery("SELECT COUNT(*) AS c FROM users WHERE uuid = '" + uuid.toString() + "';");
        try {
            if (rs.next()) {
                if (rs.getInt(1) == 0)
                    return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createUserinDatabase(Player p, JSONObject jsonObject) {
        DatabaseHandler db = new DatabaseHandler();
        String abfrage = "INSERT INTO users (money, backpack, uuid, name) VALUES (" + jsonObject.get("money") + ", '" + jsonObject.get("backpack") + "', '" + p.getUniqueId().toString() + "', '" + p.getName() + "');";
        return db.executeUpdate(abfrage);
    }

    // Below is legacy code to be compatible with the deprecated JSON-File Storage-Backend we used before.
    public JSONObject getJSONObject(UUID uuid) {
        DatabaseHandler db = new DatabaseHandler();
        String abfrage = "SELECT * FROM users WHERE uuid='" + uuid + "';";
        ResultSet rs = db.executeQuery(abfrage);
        JSONArray object = mapResultSet(rs);
        JSONParser parser = new JSONParser();
        return (JSONObject) object.get(1);
    }

    public boolean setJSONObject(UUID uuid, JSONObject jsonObject) {
        DatabaseHandler db = new DatabaseHandler();
        String abfrage = "UPDATE users SET money=" + jsonObject.get("money") + ", backpack=" + jsonObject.get("backpack") + "WHERE uuid=" + uuid.toString() + ";";
        return db.executeUpdate(abfrage);
    }


    public JSONArray mapResultSet(ResultSet rs) {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = null;
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
                        jsonObject.put(column, (Integer) value);
                    } else if (value instanceof String) {
                        jsonObject.put(column, (String) value);
                    } else if (value instanceof Boolean) {
                        jsonObject.put(column, (Boolean) value);
                    } else if (value instanceof Date) {
                        jsonObject.put(column, ((Date) value).getTime());
                    } else if (value instanceof Long) {
                        jsonObject.put(column, (Long) value);
                    } else if (value instanceof Double) {
                        jsonObject.put(column, (Double) value);
                    } else if (value instanceof Float) {
                        jsonObject.put(column, (Float) value);
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
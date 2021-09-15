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
    public DatabaseHandler db;

    public BusinessLogic() {
        db = new DatabaseHandler();
    }

    public int getMoney(String name) {
        String abfrage = "SELECT money FROM users WHERE name='" + name + "';";
        try (
                ResultSet rs = db.executeQuery(abfrage)
        ) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
        boolean res1 = writeToDB("money", Integer.toString(moneySender), "name", nameSender);
        boolean res2 = writeToDB("money", Integer.toString(moneyReceiver), "name", nameReceiver);
        if (res1 && res2)
            return true;
        throw new SQLException("Money cannot be set");

    }

    public boolean writeToDB(String field, String value, String cond, String condValue) {
        String abfrage = "UPDATE users SET '" + field + "'= '" + value + "' WHERE '" + cond + "' =  '" + condValue + "';";
        boolean res = db.executeUpdate(abfrage);
        return res;
    }

    public boolean checkUserExistsInDB(UUID uuid) {
        try (ResultSet rs = db.executeQuery("SELECT COUNT(*) AS c FROM users WHERE uuid = '" + uuid.toString() + "';")) {
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
        String abfrage = "INSERT INTO users (money, backpack, uuid, name) VALUES (" + jsonObject.get("money") + ", '" + jsonObject.get("backpack") + "', '" + p.getUniqueId().toString() + "', '" + p.getName() + "');";
        return db.executeUpdate(abfrage);
    }

    // Below is legacy code to be compatible with the deprecated JSON-File Storage-Backend we used before.
    public JSONObject getJSONObject(UUID uuid) {
        String abfrage = "SELECT * FROM users WHERE uuid='" + uuid + "';";
        try (ResultSet rs = db.executeQuery(abfrage)) {
            JSONArray object = mapResultSet(rs);
            return (JSONObject) object.get(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public boolean setJSONObject(UUID uuid, JSONObject jsonObject) {
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
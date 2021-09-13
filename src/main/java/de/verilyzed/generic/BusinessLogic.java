package de.verilyzed.generic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.sql.*;
import java.util.UUID;

public class BusinessLogic {
    private static Statement stm;

    public static void main(String[] args ) throws SQLException {
        Connection con = createConnection();
        stm = con.createStatement();
    }

    private static Connection createConnection() {
        String url = "jdbc:mysql://52.232.13.152:443/minecraft";
        String user = "root";
        String pass = "password";
        Connection con = null;
        try {
            // Verbindung aufbauen
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Verbindung erfolgreich hergestellt");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
    public boolean checkUserExistsInDB(UUID uuid) throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT COUNT(*) AS c FROM users WHERE uuid = "+ uuid + ";");
        if(rs.getInt("c") == 0)
            return false;
        return true;

    }
    // Below is legacy code to be compatible with the deprecated JSON-File Storage-Backend we used before.
    public JSONObject getJSONObject(UUID uuid) {
        try {
            String abfrage = "SELECT * FROM users WHERE uuid="+uuid+";";
            ResultSet rs = stm.executeQuery(abfrage);
            JSONArray object = mapResultSet(rs);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) object.get(1);

            return jsonObject;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean setJSONObject(UUID uuid, JSONObject jsonObject) {
        try {
            String abfrage = "UPDATE users SET money="+ jsonObject.get("money") + ", backpack=" + jsonObject.get("backpack") + "WHERE uuid=" + uuid + ";";
            stm.executeQuery(abfrage);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public void createUserinDatabase(UUID uuid, JSONObject jsonObject) {
        try {
            String abfrage = "INSERT INTO users (money, backpack, uuid) VALUES (" + jsonObject.get("money") + ", " + jsonObject.get("backpack") + ", " + uuid + ");";
            stm.executeQuery(abfrage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public JSONArray mapResultSet(ResultSet rs) throws SQLException
    {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        do
        {
            jsonObject = new JSONObject();
            for (int index = 1; index <= columnCount; index++)
            {
                String column = rsmd.getColumnName(index);
                Object value = rs.getObject(column);
                if (value == null)
                {
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
        }while(rs.next());
        return jArray;
    }
}
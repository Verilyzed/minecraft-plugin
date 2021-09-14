package de.verilyzed.generic;

import java.sql.*;

public class DatabaseHandler {
    private static final String url = "jdbc:mysql://52.232.13.152:443/minecraft";
    private static final String user = "root";
    private static final String pass = "password";
    private static Connection con;
    private Statement stm;

    public DatabaseHandler() {
        createConnection();
    }

    private void createConnection() {
        try {
            // Verbindung aufbauen
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Verbindung erfolgreich hergestellt");
            this.stm = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String abfrage) {
        ResultSet rs = null;
        try {
            rs = stm.executeQuery(abfrage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }
    public boolean executeUpdate(String abfrage) {
        try {
            stm.executeUpdate(abfrage);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

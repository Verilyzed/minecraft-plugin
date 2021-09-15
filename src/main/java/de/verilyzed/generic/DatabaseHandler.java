package de.verilyzed.generic;

import de.verilyzed.krassalla.KrassAlla;

import javax.xml.crypto.Data;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    public static Statement stm;
    public static Connection con;
    public static ResultSet rs;

    public DatabaseHandler() {
        try {
            con = KrassAlla.ds.getConnection();
            stm = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String abfrage) {
        try {
            rs = stm.executeQuery(abfrage);

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package de.verilyzed.generic;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

    public ResultSet executeQuery(String abfrage) {
        ResultSet rs = null;
        try (
                Connection con = DataSource.getInstance().getConnection();
                Statement stm = con.createStatement();
        ) {

            rs = stm.executeQuery(abfrage);
        } catch (SQLException | PropertyVetoException | IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean executeUpdate(String abfrage) {

        try (
                Connection con = DataSource.getInstance().getConnection();
                Statement stm = con.createStatement();
        ) {
            stm.executeUpdate(abfrage);
        } catch (SQLException | PropertyVetoException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

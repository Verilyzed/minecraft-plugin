package de.verilyzed.generic;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static DataSource datasource;
    private ComboPooledDataSource cpds;

    private DataSource() {
        cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:mysql://52.232.13.152:443/minecraft");
        cpds.setUser("root");
        cpds.setPassword("password");

        cpds.setInitialPoolSize(5);

        try {
            System.out.println(cpds.getNumIdleConnections() + " ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // the settings below are optional -- c3p0 can work with defaults
//        cpds.setMinPoolSize(5);
//        cpds.setAcquireIncrement(5);
//        cpds.setMaxPoolSize(20);
//        cpds.setMaxStatements(180);

    }
    public void close() {
        cpds.close();
    }

    public static DataSource getInstance() {
        if (datasource == null) {
            datasource = new DataSource();
        }
        return datasource;
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}

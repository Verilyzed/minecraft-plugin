package de.verilyzed.generic;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static DataSource datasource;
    private HikariDataSource hkds;

    private DataSource() {
        hkds = new HikariDataSource();
        hkds.setJdbcUrl("jdbc:mysql://52.232.13.152:443/minecraft");
        hkds.setUsername("root");
        hkds.setPassword("password");
        hkds.addDataSourceProperty("cachePrepStmts", "true");
        hkds.addDataSourceProperty("prepStmtCacheSize", "250");
        hkds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hkds.addDataSourceProperty("useServerPrepStmts", "true");
        hkds.addDataSourceProperty("useLocalSessionState", "true");
        hkds.addDataSourceProperty("rewriteBatchedStatements", "true");
        hkds.addDataSourceProperty("cacheResultSetMetadata", "true");
        hkds.addDataSourceProperty("cacheServerConfiguration", "true");
        hkds.addDataSourceProperty("elideSetAutoCommits", "true");
        hkds.addDataSourceProperty("maintainTimeStats", "false");


        hkds.addDataSourceProperty("maxLifetime", "30000");
    }

    public static DataSource getInstance() {
        if (datasource == null) {
            datasource = new DataSource();
        }
        return datasource;
    }

    public void close() {
        hkds.close();
    }

    public Connection getConnection() throws SQLException {
        return this.hkds.getConnection();
    }
}

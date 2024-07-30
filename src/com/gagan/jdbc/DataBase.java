package com.gagan.jdbc;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

    private final Logger LOG;

    // Constructor accepting the caller class for dynamic logging
    public DataBase(Class<?> callerClass) {
        LOG = Logger.getLogger(callerClass.getName());
        LOG.info("DataBase instance created by: " + callerClass.getName());
    }

    // Establish Connection with optional DBConfig and DatabaseType
    public Connection getConnection(DBConfig dbConfig, DBConfig.DatabaseType dbType) throws SQLException {
        LOG.info("Start :: getConnection() with dbType: " + dbType);
        if (dbConfig == null && dbType != DBConfig.DatabaseType.MANUAL) {
            dbConfig = new DBConfig(dbType, getClass());
        }

        Connection connection = null;
        try {
            if (dbType == DBConfig.DatabaseType.MANUAL && dbConfig != null) {
                // For manual, use custom DBConfig to get connection
                connection = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
                LOG.info("Current connection using Manual configuration");
            } else if (dbType == DBConfig.DatabaseType.PLATFORM) {
                // For Platform Operational DS
                // connection = Platform.getOperationalDS().getConnection();
                LOG.info("Current connection using Platform Operational DS");
            } else {
                // For MySQL and Oracle, use default DBConfig
                if (dbType == DBConfig.DatabaseType.MYSQL) {
                    dbConfig = new DBConfig(DBConfig.DatabaseType.MYSQL, getClass());
                } else if (dbType == DBConfig.DatabaseType.ORACLE) {
                    dbConfig = new DBConfig(DBConfig.DatabaseType.ORACLE, getClass());
                }
                connection = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
                LOG.info("Current connection using " + dbType + " configuration");
            }
            LOG.info("Database Connected Successfully with dbType: " + dbType);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Connection Failed", e);
            throw e;
        }
        return connection;
    }

    // Close Resources
    public void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                    LOG.info(resource.getClass().getSimpleName() + " closed successfully");
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, "Failed to close " + resource.getClass().getSimpleName(), e);
                }
            }
        }
    }
}

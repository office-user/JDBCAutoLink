package com.gagan.jdbc;


import com.gagan.jdbc.DBConfig;
import com.gagan.jdbc.DBConfig.DatabaseType;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

    private static final Logger LOG = Logger.getLogger(DataBase.class.getName());

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private DBConfig dbConfig;
    private DatabaseType dbType;

    // Establish Connection with optional DBConfig and DatabaseType
    public Connection getOIMConnection(DBConfig dbConfig, DatabaseType dbType) {
        LOG.info("Start :: getOIMConnection() with dbType: " + dbType);
        if (dbConfig == null && dbType != DatabaseType.MANUAL) {
            this.dbConfig = new DBConfig(dbType);
        } else {
            this.dbConfig = dbConfig;
        }
        this.dbType = dbType;

        try {
            // Load the JDBC driver dynamically
            Class.forName(this.dbConfig.getDriverClassName());
            connection = DriverManager.getConnection(this.dbConfig.getJdbcUrl(), this.dbConfig.getUsername(), this.dbConfig.getPassword());
            LOG.log(Level.INFO, "Database Connected Successfully with dbType: " + dbType);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "JDBC Driver not found", e);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Connection Failed", e);
        }

        return connection;
    }

    // Execute Query with Dynamic Parameters
    public ResultSet executeQuery(String query, String... params) throws SQLException {
        LOG.info("Start :: executeQuery() with dbType: " + dbType);
        if (connection == null) {
            connection = getOIMConnection(dbConfig, dbType);
        }
        if (connection == null) {
            throw new SQLException("Unable to establish a connection");
        }

        preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        LOG.log(Level.INFO, "Query executed successfully with dbType: " + dbType);
        return resultSet;
    }

    // Close Resources
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
                LOG.info("End :: resultSet.close()");
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                LOG.info("End :: preparedStatement.close()");
            }
            if (connection != null) {
                connection.close();
                LOG.info("End :: connection.close() with dbType: " + dbType);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Failed to close resources", e);
        }
    }
}
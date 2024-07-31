package com.gagan.main;

import com.gagan.jdbc.DBConfig;
import com.gagan.jdbc.DBConfig.DatabaseType;
import com.gagan.jdbc.DataBase;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private static String CLASS_NAME = Main.class.getName();
    private static Connection connection = null;
    private static final String QUERY = "SELECT * FROM users WHERE id = ?";

    public static void main(String[] args) {

        // creating variable for seeing which method currently running.
        String methodName = "Main";
        LOG.entering(CLASS_NAME, methodName); // Log
        DataBase db = new DataBase(Main.class);
        int userId = 1;

        try {
            // Change Default Value :- ORACLE, MYSQL, PLATFORM ;
            // Platform Operational DS
            // connection = db.getConnection(null, DatabaseType.MYSQL);
            // executeQueryAndLogResults(QUERY, String.valueOf(userId), db);

            // Manual
            DBConfig customDBConfig = new DBConfig(
                    "jdbc:mysql://localhost:3306/mydatabase?ssl-mode=REQUIRED",
                    "root",
                    "",
                    "com.mysql.cj.jdbc.Driver",
                    Main.class
            );
            connection = db.getConnection(customDBConfig, DatabaseType.MANUAL);
            executeQueryAndLogResults(QUERY, String.valueOf(userId), db);


        } catch (SQLException e) {
            LOG.log(Level.SEVERE, methodName + " :: SQL Exception occurred :: ", e);
        } finally {
            // Close connection
            db.close(connection);
        }
    }

    // Execute query and log results
    private static void executeQueryAndLogResults(String query, String userId, DataBase db) {

        // creating variable for seeing which method currently running.
        String methodName = "executeQueryAndLogResults()";
        LOG.entering(CLASS_NAME, methodName, new Object[] { userId });


        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Prepare statement
            preparedStatement = connection.prepareStatement(query);

            // Set parameter
            preparedStatement.setString(1, userId);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Process result set
            if (resultSet.next()) {
                LOG.info(String.format("ID: %s, Username: %s, Password Hash: %s, Email: %s, Created At: %s",
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("created_at")));
            } else {
                LOG.info(methodName + " :: No user found with ID :: " + userId);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, methodName + " :: SQL Exception occurred during query execution :: " + e);
        } finally {
            // Close resources
            db.close(resultSet, preparedStatement);
        }
    }
}

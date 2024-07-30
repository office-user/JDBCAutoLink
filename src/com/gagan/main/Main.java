package com.gagan.main;

import com.gagan.jdbc.DBConfig;
import com.gagan.jdbc.DBConfig.DatabaseType;
import com.gagan.jdbc.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Create an instance of the DataBase class
        DataBase db = new DataBase();

        // Use custom configuration for a manual database
        DBConfig customDBConfig = new DBConfig(
                "jdbc:mysql://localhost:3306/mydatabase?ssl-mode=REQUIRED",
                "root", // Database User
                "", // Database Password
                "com.mysql.cj.jdbc.Driver"  // JDBC Driver Class Name
        );

        try {
            db.getOIMConnection(customDBConfig, DatabaseType.MANUAL); // Will use custom values

            // Example query to execute
            String query = "SELECT * FROM users";
            ResultSet resultSet = db.executeQuery(query); // Will use custom values

            // Process the result set
            while (resultSet.next()) {
                LOG.info(String.format("ID: %s, Username: %s, Password Hash: %s, Email: %s, Created At: %s, Updated At: %s",
                        resultSet.getString("id"), resultSet.getString("username"), resultSet.getString("password_hash"),
                        resultSet.getString("email"), resultSet.getString("created_at"), resultSet.getString("updated_at")));
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "SQL Exception occurred", e);
        } finally {
            // Close the resources
            db.close();
        }

    }
}


# JDBCAutoLink
 this is


for Automaticlly connection with Default value use this code
```
// Use default configuration for  MYSQL (DB1) or ORACLE(DB2)
try {
db.getOIMConnection(null, DatabaseType.MYSQL); // Will use default values for DB

            // Example query to execute
            String query = "SELECT * FROM users";
            ResultSet resultSet = db.executeQuery(query); // Will use default values for DB

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
```

for Manual Connection use this code

```
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
```


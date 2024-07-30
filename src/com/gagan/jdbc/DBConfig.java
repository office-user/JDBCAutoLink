package com.gagan.jdbc;

import java.util.logging.Logger;

public class DBConfig {

    private final Logger LOG;

    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;

    // Default values for MySQL
    private static final String DEFAULT_DB1_JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase?ssl-mode=REQUIRED";
    private static final String DEFAULT_DB1_USERNAME = "root";
    private static final String DEFAULT_DB1_PASSWORD = "";
    private static final String DEFAULT_DB1_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    // Default values for ORACLE SQL DEVELOPER
    private static final String DEFAULT_DB2_JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase?ssl-mode=REQUIRED";
    private static final String DEFAULT_DB2_USERNAME = "root";
    private static final String DEFAULT_DB2_PASSWORD = "";
    private static final String DEFAULT_DB2_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    // Enum to distinguish between different databases
    public enum DatabaseType {
        MYSQL,
        ORACLE,
        MANUAL,
        PLATFORM
    }

    public DBConfig(Class<?> callerClass) {
        this(DatabaseType.MYSQL, callerClass);
    }

    public DBConfig(DatabaseType dbType, Class<?> callerClass) {
        LOG = Logger.getLogger(callerClass.getName());
        if (dbType == DatabaseType.MYSQL) {
            this.jdbcUrl = DEFAULT_DB1_JDBC_URL;
            this.username = DEFAULT_DB1_USERNAME;
            this.password = DEFAULT_DB1_PASSWORD;
            this.driverClassName = DEFAULT_DB1_DRIVER_CLASS_NAME;
        } else if (dbType == DatabaseType.ORACLE) {
            this.jdbcUrl = DEFAULT_DB2_JDBC_URL;
            this.username = DEFAULT_DB2_USERNAME;
            this.password = DEFAULT_DB2_PASSWORD;
            this.driverClassName = DEFAULT_DB2_DRIVER_CLASS_NAME;
        }
    }

    // Default
    public DBConfig(String jdbcUrl, String username, String password, String driverClassName, Class<?> callerClass) {
        LOG = Logger.getLogger(callerClass.getName());
        this.jdbcUrl = (jdbcUrl != null && !jdbcUrl.isEmpty()) ? jdbcUrl : DEFAULT_DB1_JDBC_URL;
        this.username = (username != null && !username.isEmpty()) ? username : DEFAULT_DB1_USERNAME;
        this.password = (password != null && !password.isEmpty()) ? password : DEFAULT_DB1_PASSWORD;
        this.driverClassName = (driverClassName != null && !driverClassName.isEmpty()) ? driverClassName : DEFAULT_DB1_DRIVER_CLASS_NAME;
    }

    // Overloaded constructor for specifying database type with custom values
    public DBConfig(DatabaseType dbType, String jdbcUrl, String username, String password, String driverClassName, Class<?> callerClass) {
        LOG = Logger.getLogger(callerClass.getName());
        if (dbType == DatabaseType.MANUAL) {
            this.jdbcUrl = jdbcUrl;
            this.username = username;
            this.password = password;
            this.driverClassName = driverClassName;
        } else if (dbType == DatabaseType.MYSQL) {
            this.jdbcUrl = (jdbcUrl != null && !jdbcUrl.isEmpty()) ? jdbcUrl : DEFAULT_DB1_JDBC_URL;
            this.username = (username != null && !username.isEmpty()) ? username : DEFAULT_DB1_USERNAME;
            this.password = (password != null && !password.isEmpty()) ? password : DEFAULT_DB1_PASSWORD;
            this.driverClassName = (driverClassName != null && !driverClassName.isEmpty()) ? driverClassName : DEFAULT_DB1_DRIVER_CLASS_NAME;
        } else if (dbType == DatabaseType.ORACLE) {
            this.jdbcUrl = (jdbcUrl != null && !jdbcUrl.isEmpty()) ? jdbcUrl : DEFAULT_DB2_JDBC_URL;
            this.username = (username != null && !username.isEmpty()) ? username : DEFAULT_DB2_USERNAME;
            this.password = (password != null && !password.isEmpty()) ? password : DEFAULT_DB2_PASSWORD;
            this.driverClassName = (driverClassName != null && !driverClassName.isEmpty()) ? driverClassName : DEFAULT_DB2_DRIVER_CLASS_NAME;
        }
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }
}

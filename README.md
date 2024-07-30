# JDBCAutoLink
 this is


for Automaticlly connection with Default value use this code
```
// Change Default Value :- ORACLE, MYSQL, PLATFORM ;
// Platform Operational DS
connection = db.getConnection(null, DatabaseType.ORACLE);
executeQueryAndLogResults(QUERY, String.valueOf(userId), db);
```

for Manual Connection use this code
```
// Manual
DBConfig customDBConfig = new DBConfig(
"jdbc:mysql://localhost:3306/mydatabase?ssl-mode=REQUIRED",
"root",
"",
"com.mysql.cj.jdbc.Driver",
Main.class  // Entry Chacking Class name passing to DataBase Logger
);
connection = db.getConnection(customDBConfig, DatabaseType.MANUAL);
executeQueryAndLogResults(QUERY, String.valueOf(userId), db);
```


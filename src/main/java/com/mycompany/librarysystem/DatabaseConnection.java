package com.mycompany.librarysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://hattie.db.elephantsql.com:5432/oaehwzla";
    private static final String DB_USER = "oaehwzla";
    private static final String DB_PASSWORD = "aj3XjlSmghfN4LGUZE12mOfUTDaKXiJY";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("PostgreSQL JDBC driver not found");
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

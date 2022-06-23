package com.kma.fitnesssmc.data.local;

import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class FitnessDatabase {
    private static final String DATABASE_HOST = "localhost";
    private static final String DATABASE_NAME = "fitness";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_PASSWORD = null;

    private static FitnessDatabase instance = null;

    private Connection connection = null;

    private FitnessDatabase() {

    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + DATABASE_HOST + "/" + DATABASE_NAME, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    public boolean executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeUpdate(query) == 1;
    }

    public void close() throws SQLException {
        connection.close();
    }

    public static @NotNull FitnessDatabase getInstance() {
        return instance == null ? instance = new FitnessDatabase() : instance;
    }
}

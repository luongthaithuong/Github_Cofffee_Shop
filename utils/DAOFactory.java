package com.example.coffee_rubi_ver.utils;

import com.example.coffee_rubi_ver.connection.ConnectionFactory;

import java.sql.Connection;

public class DAOFactory {
    private static DAOFactory instance;

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    private DAOFactory() {
        super();
    }

    public Connection getConnection() {
        Connection connection = ConnectionFactory.getInstance().connect();
        return connection;
    }
}

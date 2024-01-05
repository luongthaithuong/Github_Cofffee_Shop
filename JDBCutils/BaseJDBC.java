package com.example.coffee_rubi_ver.JDBCutils;

import com.example.coffee_rubi_ver.utils.DAOFactory;

import java.sql.Connection;

public class BaseJDBC {
    public Connection getConnection() {
        return DAOFactory.getInstance().getConnection();
    }
}

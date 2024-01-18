package com.example.coffee_rubi_ver.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3307/coffee_store_ver2?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String MAX_POOL = "50";

    private Properties properties;

    private static ConnectionFactory instance;

    private ConnectionFactory()
    {
        super();
    }

    public static ConnectionFactory getInstance()
    {
        if (instance == null)
        {
            instance = new ConnectionFactory();
        }
        return instance;
    }


    public Properties getProperties ()
    {
        if (properties == null)
        {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName(DATABASE_DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, getProperties());
        } catch (ClassNotFoundException e) {
            System.out.println("Lỗi Driver");
        } catch (SQLException e) {
            System.out.println("Lỗi chuỗi kết nối: "+e.getMessage());
        }
        return conn;
    }
    public void disconnect(Connection conn) {
        if (conn != null)
        {
            try {
                conn.close();
                System.out.println("connection close");
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}

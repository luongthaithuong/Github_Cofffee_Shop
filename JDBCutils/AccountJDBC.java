package com.example.coffee_rubi_ver.JDBCutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.coffee_rubi_ver.model.login.Account;
import org.apache.commons.dbutils.DbUtils;


public class AccountJDBC extends BaseJDBC {
    public boolean checkLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean checkLogin1 = false;
        try {
            conn = getConnection();
            String sql = "select username, password from account where username = ? and password = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();
            if (rs.next())
                checkLogin1 = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(rs);
        }
        return checkLogin1;
    }

    public boolean checkNewAccount(String account)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            conn = getConnection();
            String sql = "select username from account where username = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,account);
            rs = statement.executeQuery();
            while (rs.next())
            {
                check = true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(rs);
        }
        return check;
    }

    public void insertNewAccount(Account account)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            String sql = "insert into account(`username`, `password`, `role`) values (?,?,?)";
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1,account.getUsername());
            statement.setString(2,account.getPassword());
            statement.setInt(3,account.getRole());
            statement.executeUpdate();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(statement);
        }
    }
}

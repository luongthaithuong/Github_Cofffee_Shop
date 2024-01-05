package com.example.coffee_rubi_ver.JDBCutils;

import com.example.coffee_rubi_ver.model.table.TableType;
import javafx.collections.FXCollections;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TableTypeJDBC extends BaseJDBC{
    public List<TableType> listTableTypeDatabase()
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<TableType> data = FXCollections.observableArrayList();
        try {
            conn = getConnection();
            String sql = "select * from tabletypename";
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next())
            {
                TableType tableType = new TableType();
                tableType.setId(rs.getInt("id"));
                tableType.setTableTypeName(rs.getString("table_type_name"));
                data.add(tableType);
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
        return data;
    }

    public TableType getTableTypeByName(String name)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        TableType tableType = null;
        try {
            conn = getConnection();
            String sql = "select * from tabletypename where table_type_name = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            rs = statement.executeQuery();
            while (rs.next())
            {
                tableType = new TableType();
                tableType.setId(rs.getInt("id"));
                tableType.setTableTypeName(name);
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
        return tableType;
    }

    public TableType getTableTypeByID(int id)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        TableType tableType = null;
        try {
            conn = getConnection();
            String sql = "select * from tabletypename where id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next())
            {
                tableType = new TableType();
                tableType.setTableTypeName(rs.getString("table_type_name"));
                tableType.setId(id);
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
        return tableType;
    }

    public void insertNewTableType(TableType tableType) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("INSERT INTO tabletypename (table_type_name) VALUES(?)");
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, tableType.getTableTypeName());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public void deleteTableTypeName(String tableTypeName) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("DELETE FROM `coffee_store_ver2`.`tabletypename` WHERE (`table_type_name` = ?)");
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, tableTypeName);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
}

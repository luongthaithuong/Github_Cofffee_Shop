package com.example.coffee_rubi_ver.JDBCutils;

import com.example.coffee_rubi_ver.model.product.Product;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import javafx.collections.FXCollections;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TableJDBC extends BaseJDBC{
    public void insertTableName(TableCoffee tableCoffee) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();

            //query
            StringBuilder sql = new StringBuilder("INSERT INTO tablecoffee (`tablename`, `status`, `tabletypename`) VALUES(?,?,?)");
            System.out.println("insert new product "+sql.toString());
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, tableCoffee.getTableName());
            statement.setInt(2, tableCoffee.getStatus());
            statement.setInt(3, tableCoffee.getTableType());
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public List<TableCoffee> getTableCoffeeByType(int type)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<TableCoffee> data = FXCollections.observableArrayList();
        try {
            conn = getConnection();
            String sql = "select * from tablecoffee where tabletypename = ? order by tablename + 0 asc";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,type);
            rs = statement.executeQuery();
            while (rs.next())
            {
                TableCoffee tableCoffee = new TableCoffee();
                tableCoffee.setId(rs.getInt("id"));
                tableCoffee.setTableName(rs.getString("tablename"));
                tableCoffee.setStatus(rs.getInt("status"));
                data.add(tableCoffee);
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
    public TableCoffee getTableCoffeeById(int id)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        TableCoffee tableCoffee = null;
        try {
            conn = getConnection();
            String sql = "select * from tablecoffee where id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            rs = statement.executeQuery();
            while (rs.next())
            {
                tableCoffee = new TableCoffee();
                tableCoffee.setId(rs.getInt("id"));
                tableCoffee.setTableName(rs.getString("tablename"));
                tableCoffee.setStatus(rs.getInt("status"));
                tableCoffee.setTableType(rs.getInt("tabletypename"));
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
        return tableCoffee;
    }
    public void updateStatus1TableByIdTable(int id)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            coon = getConnection();

            String sql = "UPDATE tablecoffee set status = 1 Where id = ?";
            statement = coon.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
    }
    public void updateStatus0TableByIdTable(int id)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            coon = getConnection();

            String sql = "UPDATE tablecoffee set status = 0 Where id = ?";
            statement = coon.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
    }
    public void editTable(String tableName, int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("UPDATE tablecoffee SET tablename = ? WHERE id = ?");
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, tableName);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public void deleteTableCoffee(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("DELETE FROM tablecoffee WHERE id = ?");
            statement = conn.prepareStatement(sql.toString());
            statement.setInt(1, id);
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

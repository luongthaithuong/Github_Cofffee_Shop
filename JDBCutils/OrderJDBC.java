package com.example.coffee_rubi_ver.JDBCutils;

import com.example.coffee_rubi_ver.model.order.OrderDatabase;
import com.example.coffee_rubi_ver.model.table.TableCoffee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderJDBC extends BaseJDBC{

    public OrderDatabase getOrderDatabaseById(int tableId)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        OrderDatabase orderDatabase = null;
        try {
            coon = getConnection();

            String sql = "select * from ordercoffee where tableid=? and status=1";

            statement = coon.prepareStatement(sql);
            statement.setInt(1,tableId);
//            statement.setInt(2, status);
            rs = statement.executeQuery();
            while (rs.next())
            {
                orderDatabase = new OrderDatabase();
                orderDatabase.setId(rs.getInt("id"));
                orderDatabase.setTableId(rs.getInt("tableid"));
                orderDatabase.setProductOnOrder(rs.getString("productonorder"));
                orderDatabase.setStatus(rs.getInt("status"));
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
        return orderDatabase;
    }

    public void insertNewOrder(OrderDatabase orderDatabase) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();

            //query
            StringBuilder sql = new StringBuilder("INSERT INTO ordercoffee(`tableid`, `productonorder`, `status`,`totalprice`) VALUES(?,?,?,?)");
            System.out.println("insert new order " + sql.toString());
            statement = conn.prepareStatement(sql.toString());
            statement.setInt(1, orderDatabase.getTableId());
            statement.setString(2, orderDatabase.getProductOnOrder());
            statement.setInt(3, orderDatabase.getStatus());
            statement.setInt(4,orderDatabase.getTotalPrice());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public void updateOrder(int id, OrderDatabase orderDatabase)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            coon = getConnection();

            String sql = "UPDATE ordercoffee set productonorder = ?, totalprice = ? where id = ? and status=1";
            statement = coon.prepareStatement(sql);
            statement.setString(1, orderDatabase.getProductOnOrder());
            statement.setInt(2,orderDatabase.getTotalPrice());
            statement.setInt(3, id);
            statement.executeUpdate();
            System.out.println(rs);
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
    }
    public void updateChangeTable(int id, int idTable)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            coon = getConnection();

            String sql = "UPDATE ordercoffee set tableid = ? where id = ?";
            statement = coon.prepareStatement(sql);
            statement.setInt(1, idTable);
            statement.setInt(2,id);

            statement.executeUpdate();
            System.out.println(rs);
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
    }
    public void updateStatus2Orderdatabase(int id)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            coon = getConnection();

            String sql = "UPDATE ordercoffee set status = 2 where id = ?";
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
    public ObservableList<OrderDatabase> showTodayBCDT()
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<OrderDatabase> orderDatabaseList = FXCollections.observableArrayList();
        try {
            coon = getConnection();

            String sql = "SELECT * FROM ordercoffee WHERE new_date_time >= CURDATE() && new_date_time < (CURDATE() + INTERVAL 1 DAY) and status=2";
            statement = coon.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next())
            {
                OrderDatabase orderDatabase = new OrderDatabase();
                orderDatabase.setId(rs.getInt("id"));
                orderDatabase.setTableId(rs.getInt("tableid"));
                orderDatabase.setProductOnOrder(rs.getString("productonorder"));
                orderDatabase.setStatus(rs.getInt("status"));
                orderDatabase.setTotalPrice(rs.getInt("totalprice"));
                orderDatabase.setDate(rs.getString("new_date_time"));
                orderDatabaseList.add(orderDatabase);
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
        return orderDatabaseList;
    }
    public ObservableList<OrderDatabase> showMouthBCDT()
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<OrderDatabase> orderDatabaseList = FXCollections.observableArrayList();
        try {
            coon = getConnection();

            String sql = "select * from ordercoffee\n" +
                    "       where MONTH(new_date_time) = MONTH(now())\n" +
                    "       and YEAR(new_date_time) = YEAR(now()) and status=2";
            statement = coon.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next())
            {
                OrderDatabase orderDatabase = new OrderDatabase();
                orderDatabase.setId(rs.getInt("id"));
                orderDatabase.setTableId(rs.getInt("tableid"));
                orderDatabase.setProductOnOrder(rs.getString("productonorder"));
                orderDatabase.setStatus(rs.getInt("status"));
                orderDatabase.setTotalPrice(rs.getInt("totalprice"));
                orderDatabase.setDate(rs.getString("new_date_time"));
                orderDatabaseList.add(orderDatabase);
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
        return orderDatabaseList;
    }
}

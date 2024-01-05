package com.example.coffee_rubi_ver.JDBCutils;

import com.example.coffee_rubi_ver.model.product.Product;
import javafx.collections.FXCollections;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ProductJDBC extends BaseJDBC{
    public void insertProduct(Product product) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();

            //query
            StringBuilder sql = new StringBuilder("INSERT INTO product (`productname`, `productprice`, `category`) VALUES(?,?,?)");
            System.out.println("insert new product "+sql.toString());
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getProductPrice());
            statement.setInt(3, product.getCategory());
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
    public List<Product> getProductByCatID(int id)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Product> data = FXCollections.observableArrayList();
        try {
            conn = getConnection();
            String sql = "select * from product where category = ? order by productprice asc";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            rs = statement.executeQuery();
            while (rs.next())
            {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setProductName(rs.getString("productname"));
                product.setProductPrice(rs.getInt("productprice"));
                data.add(product);
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
    public Product getProductByProductId(int id)
    {
        Connection coon = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Product product = null;
        try {
            coon = getConnection();

            String sql = "select * from product where id = ?";
            statement = coon.prepareStatement(sql);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            while (rs.next())
            {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setProductName(rs.getString("productname"));
                product.setProductPrice(rs.getInt("productprice"));
                product.setCategory(rs.getInt("category"));
//                System.out.println("getProductByProductId : " + product.getProductName());
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(coon);
        }
//        System.out.println("getProductByProductId : " + product.getProductName());
        return product;
    }
    public void editProduct(String productName,int productPrice, int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("UPDATE product SET productname = ?, productprice = ? WHERE id = ?");
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, productName);
            statement.setInt(2,productPrice);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public void deleteProduct(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("DELETE FROM product WHERE id = ?");
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

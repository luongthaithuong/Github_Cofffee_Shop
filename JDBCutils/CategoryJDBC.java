package com.example.coffee_rubi_ver.JDBCutils;

import com.example.coffee_rubi_ver.model.product.Category;
import javafx.collections.FXCollections;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CategoryJDBC extends BaseJDBC{
    public void insertNewCategory(Category category) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("INSERT INTO category (category_name) VALUES(?)");
            System.out.println("insert new Category " + sql.toString());
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, category.getCategoryName());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public void deleteCategoryName(String categoryName) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //connect
            conn = getConnection();
            //query
            StringBuilder sql = new StringBuilder("DELETE FROM category WHERE category_name = ?");
            statement = conn.prepareStatement(sql.toString());
            statement.setString(1, categoryName);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(conn);
        }
    }
    public List<Category> listCategoryDatabase()
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Category> data = FXCollections.observableArrayList();
        try {
            conn = getConnection();
            String sql = "select * from category";
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next())
            {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setCategoryName(rs.getString("category_name"));
                data.add(category);
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
    public Category getCategoryByName(String name)
    {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Category category = null;
        try {
            conn = getConnection();
            String sql = "select * from category where category_name = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            rs = statement.executeQuery();
            while (rs.next())
            {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setCategoryName(name);
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
        return category;
    }
}

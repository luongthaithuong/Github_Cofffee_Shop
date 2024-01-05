package com.example.coffee_rubi_ver.model.order;

import java.util.HashSet;
import java.util.Set;

public class OrderDatabase {
    Set<OrderTemp> orderTempSetSet = new HashSet<>();
    private int id;
    private String tableTypeName;
    private String tableName;
    private int tableId;
    private String productOnOrder;
    private int status;

    private int totalPrice;

    private String date;


    public OrderDatabase() {
    }

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<OrderTemp> getOrderTempSet() {
        return orderTempSetSet;
    }

    public void setOrderTempSet(Set<OrderTemp> orderTempSetSet) {
        this.orderTempSetSet = orderTempSetSet;
    }

    public OrderDatabase(int id, int tableId, String productOnOrder, int status,int totalPrice, String date) {
        this.id = id;
        this.tableId = tableId;
        this.productOnOrder = productOnOrder;
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableName) {
        this.tableId = tableName;
    }

    public String getProductOnOrder() {
        return productOnOrder;
    }

    public void setProductOnOrder(String productOnOrder) {
        this.productOnOrder = productOnOrder;
    }

    @Override
    public String toString() {
        return tableTypeName +"\t"
                + tableName +"\t"+
                "\t" + productOnOrder +"\t"+
                "Tổng Tiền = " + totalPrice +"\t-\t"+
                "Ngày : " + date ;
    }
}

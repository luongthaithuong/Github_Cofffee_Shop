package com.example.coffee_rubi_ver.model.table;

public class TableCoffee {
    private int id;
    private String tableName;
    private int status;
    private int tableType;

    public TableCoffee() {
    }

    public TableCoffee(int id, String tableName, int status, int tableType) {
        this.id = id;
        this.tableName = tableName;
        this.status = status;
        this.tableType = tableType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
    }

    @Override
    public String toString() {
        return "TableCoffee{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", status=" + status +
                ", tableType=" + tableType +
                '}';
    }
}

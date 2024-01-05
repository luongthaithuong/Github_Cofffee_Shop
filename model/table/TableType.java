package com.example.coffee_rubi_ver.model.table;

public class TableType {
    private int id;
    private String tableTypeName;

    public TableType() {
    }

    public TableType(int id, String tableTypeName) {
        this.id = id;
        this.tableTypeName = tableTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }
}

package edu.upvictoria.datamodeler.models;

import java.util.*;

public class Table {
    private String tableName;
    private HashMap<String, String> columns;
    private Set<String> primaryKeys;
    private List<ForeignKey> foreignKeys;

    public Table(String tableName) {
        this.tableName = tableName;
        columns = new HashMap<>();
        primaryKeys = new HashSet<>();
        foreignKeys = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public HashMap<String, String> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<String, String> columns) {
        this.columns = columns;
    }

    public void addColumn(String columnName, String columnType) {
        columns.put(columnName, columnType);
    }

    public Set<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void addPrimaryKey(String primaryKey) {
        primaryKeys.add(primaryKey);
    }

    public void setPrimaryKeys(Set<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        foreignKeys.add(foreignKey);
    }
}

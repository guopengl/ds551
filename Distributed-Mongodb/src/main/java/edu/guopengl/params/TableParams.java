package edu.guopengl.params;

public class TableParams {
    private int id;
    private String tableName;
    private String partitionKey;

    public TableParams(int id, String tableName, String partitionKey) {
        this.id = id;
        this.tableName = tableName;
        this.partitionKey = partitionKey;
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

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }
}

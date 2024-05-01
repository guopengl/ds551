package edu.guopengl.params;

public class CreateCollectionParams {
    private String collectionName;
    private String partitionKey;

    public CreateCollectionParams(){}
    public CreateCollectionParams(String collectionName, String partitionKey) {
        this.collectionName = collectionName;
        this.partitionKey = partitionKey;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }
}

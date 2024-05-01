package edu.guopengl.params;

public class DropCollectionParams {
    private String collectionName;

    public DropCollectionParams(String collectionName) {
        this.collectionName = collectionName;
    }

    public DropCollectionParams() {
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}

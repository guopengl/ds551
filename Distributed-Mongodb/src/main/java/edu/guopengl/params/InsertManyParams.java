package edu.guopengl.params;

import org.bson.Document;

import java.util.List;

public class InsertManyParams {
    private String collectionName;
    private List<Document> data;

    public InsertManyParams() {
    }

    public InsertManyParams(String collectionName, List<Document> data) {
        this.collectionName = collectionName;
        this.data = data;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<Document> getData() {
        return data;
    }

    public void setData(List<Document> data) {
        this.data = data;
    }
}

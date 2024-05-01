package edu.guopengl.params;

import org.bson.Document;

public class UpdateOneParams {
    private String collectionName;
    private Document query;
    private Document update;

    public UpdateOneParams() {
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Document getQuery() {
        return query;
    }

    public void setQuery(Document query) {
        this.query = query;
    }

    public Document getUpdate() {
        return update;
    }

    public void setUpdate(Document update) {
        this.update = update;
    }
}

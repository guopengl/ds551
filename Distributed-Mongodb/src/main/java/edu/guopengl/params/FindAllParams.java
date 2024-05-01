package edu.guopengl.params;

import org.bson.Document;

public class FindAllParams {
    private String collectionName;
    private Document query;

    public FindAllParams() {
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
}

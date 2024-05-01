package edu.guopengl.config;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import edu.guopengl.params.TableParams;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class ClusterConfig {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static int numDatabases;
    private static final LinkedHashMap<String, TableParams> tableInfos = new LinkedHashMap<>();

    @Autowired
    private MongoClient mongoClient;
    @PostConstruct
    private void postConstruct(){
        MongoCollection<Document> conf = mongoClient.getDatabase("db0").getCollection("conf");
        numDatabases = conf.find().first().getInteger("numDatabases");
        for(Document document : conf.find().skip(1)){
            TableParams tableParams = new TableParams(document.getInteger("id"),
                    document.getString("tableName"), document.getString("partitionKey"));
            tableInfos.put(document.getString("tableName"), tableParams);
        }
    }

    public static ReentrantReadWriteLock getRwLock() {
        return rwLock;
    }

    public static int getNumDatabases() {
        return numDatabases;
    }

    public static void setNumDatabases(int _numDatabases) {
        numDatabases = _numDatabases;
    }

    public static LinkedHashMap<String, TableParams> getTablesInfo() {
        return tableInfos;
    }

}

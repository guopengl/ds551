package edu.guopengl.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.guopengl.config.ClusterConfig;
import edu.guopengl.controller.response.Response;
import edu.guopengl.params.CreateCollectionParams;
import edu.guopengl.params.DropCollectionParams;
import edu.guopengl.params.TableParams;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DDLService {
    @Autowired
    private MongoClient mongoClient;

    public List<Document> showClusters(){
        List<Document> clustersInfo = new ArrayList<>();
        ClusterConfig.getRwLock().readLock().lock();
        try{
            int numClusters = ClusterConfig.getNumDatabases();
            for(int i = 0; i < numClusters; i++) {
                MongoDatabase database = mongoClient.getDatabase("db" + i);
                // Extract the size information from the command result
                Document stats = database.runCommand(new Document("dbStats", 1));
                stats.remove("$clusterTime");
                stats.remove("operationTime");
                clustersInfo.add(stats);
            }
        } finally {
            ClusterConfig.getRwLock().readLock().unlock();
        }
        return clustersInfo;
    }

    public Collection<TableParams> showTables(){
        LinkedHashMap<String, TableParams> res = new LinkedHashMap<>();
        ClusterConfig.getRwLock().readLock().lock();
        try{
            res = ClusterConfig.getTablesInfo();
        } finally {
            ClusterConfig.getRwLock().readLock().unlock();
        }
        return res.values();
    }

    public boolean createCollection(CreateCollectionParams request){
        String collectionName = request.getCollectionName();
        //already exists
        ClusterConfig.getRwLock().writeLock().lock();
        try{
            if(ClusterConfig.getTablesInfo().containsKey(collectionName)){
                return false;
            }

            ClientSession session = mongoClient.startSession();
            try{
                session.startTransaction();
                for(int i = 0; i < ClusterConfig.getNumDatabases(); i++){
                    mongoClient.getDatabase("db" + i).createCollection(session, collectionName);
                }
                Document doc = new Document().append("id", ClusterConfig.getTablesInfo().size())
                        .append("tableName", collectionName).append("partitionKey", request.getPartitionKey());
                mongoClient.getDatabase("db0").getCollection("conf").insertOne(session, doc);
                ClusterConfig.getTablesInfo().put(collectionName, new TableParams(
                        ClusterConfig.getTablesInfo().size(), collectionName, request.getPartitionKey()
                ));
                session.commitTransaction();
            } catch (Exception e){
                session.abortTransaction();
                throw e;
            } finally {
                session.close();
            }
        } finally {
            ClusterConfig.getRwLock().writeLock().unlock();
        }
        return true;
    }

    public boolean dropCollection(DropCollectionParams request){
        String collectionName = request.getCollectionName();

        ClusterConfig.getRwLock().writeLock().lock();
        try{
            if(! ClusterConfig.getTablesInfo().containsKey(collectionName)){
                return false;
            }

            ClientSession session = mongoClient.startSession();
            try{
                session.startTransaction();
                for(int i = 0; i < ClusterConfig.getNumDatabases(); i++){
                    mongoClient.getDatabase("db" + i).getCollection(collectionName).drop();
                }
                mongoClient.getDatabase("db0").getCollection("conf")
                        .deleteOne(session, Filters.eq("tableName", collectionName));
                ClusterConfig.getTablesInfo().remove(collectionName);
                session.commitTransaction();
            } catch (Exception e){
                session.abortTransaction();
                throw e;
            } finally {
                session.close();
            }
        } finally {
            ClusterConfig.getRwLock().writeLock().unlock();
        }
        return true;
    }
}

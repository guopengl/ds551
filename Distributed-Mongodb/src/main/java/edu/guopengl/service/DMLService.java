package edu.guopengl.service;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import edu.guopengl.config.ClusterConfig;
import edu.guopengl.params.DeleteOneParams;
import edu.guopengl.params.FindAllParams;
import edu.guopengl.params.InsertManyParams;
import edu.guopengl.params.UpdateOneParams;
import edu.guopengl.utils.HashUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DMLService {
    @Autowired
    private MongoClient mongoClient;

    public void insertMany(InsertManyParams request) throws Exception {
        String collectionName = request.getCollectionName();
        List<Document> data = request.getData();

        ClusterConfig.getRwLock().writeLock().lock();
        try{
            //collection has not been created
            if(! ClusterConfig.getTablesInfo().containsKey(collectionName)){
                throw new Exception("collection '" + collectionName + "' does not exist.");
            }
            //hash buckets
            List<ArrayList<Document>> buckets = new ArrayList<>();
            for (int i = 0; i < ClusterConfig.getNumDatabases(); i++) {
                buckets.add(new ArrayList<>());
            }

            for(Document doc : request.getData()){
                int hashIndex = HashUtil.getHashIndex(collectionName, doc);
                if(hashIndex < 0){
                    throw new Exception("data must has partitionKey field.");
                }
                buckets.get(hashIndex).add(doc);
            }

            ClientSession session = mongoClient.startSession();
            try {
                session.startTransaction();
                for(int i = 0; i < ClusterConfig.getNumDatabases(); i++){
                    ArrayList<Document> bucket = buckets.get(i);
                    if(bucket.size() > 0){
                        mongoClient.getDatabase("db"+i).getCollection(collectionName).insertMany(session, bucket);
                    }
                }
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
    }

    public void updateOne(UpdateOneParams request) throws Exception {
        String collectionName = request.getCollectionName();
        String partitionKey = ClusterConfig.getTablesInfo().get(collectionName).getPartitionKey();
        Document query = request.getQuery();
        Document update = request.getUpdate(); //{}, only set equal allowed
        int hashIndex = HashUtil.getHashIndex(collectionName, query);

        ClusterConfig.getRwLock().writeLock().lock();
        try{
            //collection has not been created
            if(! ClusterConfig.getTablesInfo().containsKey(collectionName)){
                throw new Exception("collection '" + collectionName + "' does not exist.");
            }

            if(update.containsKey(partitionKey)){
                //locate the document to be modified
                Document findRes = null;
                if(hashIndex >= 0){
                    findRes = mongoClient.getDatabase("db"+hashIndex).
                            getCollection(collectionName).find(query).first();
                } else{
                    for (int i = 0; i < ClusterConfig.getNumDatabases(); i++) {
                        findRes = mongoClient.getDatabase("db"+i).
                                getCollection(collectionName).find(query).first();
                        if(findRes != null){
                            hashIndex = i;
                            break;
                        }
                    }
                }
                //did not find such document
                if(findRes == null){
                    return;
                }

                int newHashIndex = HashUtil.getHashIndex(collectionName, update);
                //hash index remains the same
                if(hashIndex == newHashIndex){
                    mongoClient.getDatabase("db"+hashIndex).
                            getCollection(collectionName).updateOne(query, new Document("$set", update));
                } else{
                    ClientSession session = mongoClient.startSession();
                    try {
                        session.startTransaction();
                        //delete from old, insert into new, update
                        mongoClient.getDatabase("db"+hashIndex).
                                getCollection(collectionName).deleteOne(session, findRes);
                        mongoClient.getDatabase("db"+newHashIndex).
                                getCollection(collectionName).insertOne(session, findRes);
                        mongoClient.getDatabase("db"+newHashIndex).
                                getCollection(collectionName).updateOne(session, findRes, new Document("$set", update));
                        session.commitTransaction();
                    } catch (Exception e){
                        session.abortTransaction();
                        throw e;
                    } finally {
                        session.close();
                    }
                }
            } else{ //no update on partitionKey, just update, no transfer between dbs
                //know exact db
                if(hashIndex >= 0){
                    mongoClient.getDatabase("db"+hashIndex).
                            getCollection(collectionName).updateOne(query, new Document("$set", update));
                } else{ //have to traverse all dbs
                    for (int i = 0; i < ClusterConfig.getNumDatabases(); i++) {
                        UpdateResult res = mongoClient.getDatabase("db"+i).
                                getCollection(collectionName).updateOne(query, new Document("$set", update));
                        if(res.getModifiedCount() == 1){
                            return;
                        }
                    }
                }
            }
        } finally {
            ClusterConfig.getRwLock().writeLock().unlock();
        }
    }

    public void deleteOne(DeleteOneParams request) throws Exception {
        String collectionName = request.getCollectionName();
        Document query = request.getQuery();
        int hashIndex = HashUtil.getHashIndex(collectionName, query);

        ClusterConfig.getRwLock().writeLock().lock();
        try{
            if(! ClusterConfig.getTablesInfo().containsKey(collectionName)){
                throw new Exception("collection '" + collectionName + "' does not exist.");
            }

            if(hashIndex >= 0){
                mongoClient.getDatabase("db"+hashIndex).
                        getCollection(collectionName).deleteOne(query);
            } else{
                for (int i = 0; i < ClusterConfig.getNumDatabases(); i++) {
                    DeleteResult res = mongoClient.getDatabase("db"+i).
                            getCollection(collectionName).deleteOne(query);
                    if(res.getDeletedCount() == 1){
                        return;
                    }
                }
            }
        } finally {
            ClusterConfig.getRwLock().writeLock().unlock();
        }
    }

    public List<Document> find(FindAllParams request) throws Exception {
        List<Document> res = new ArrayList<>();
        String collectionName = request.getCollectionName();
        Document query = request.getQuery();
        int hashIndex = HashUtil.getHashIndex(collectionName, query);

        ClusterConfig.getRwLock().readLock().lock();
        try{
            if(! ClusterConfig.getTablesInfo().containsKey(collectionName)){
                throw new Exception("collection '" + collectionName + "' does not exist.");
            }

            if(hashIndex >= 0){
                mongoClient.getDatabase("db"+hashIndex).
                        getCollection(collectionName).find(query).into(res);
            } else{
                for (int i = 0; i < ClusterConfig.getNumDatabases(); i++) {
                    mongoClient.getDatabase("db"+i).
                            getCollection(collectionName).find(query).into(res);
                }
            }
        } finally {
            ClusterConfig.getRwLock().readLock().unlock();
        }
        return res;
    }
}

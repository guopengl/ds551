package edu.guopengl.controller;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.logging.Filter;

@Controller
public class TestController {

    @Autowired
    private MongoClient mongoClient;
    @RequestMapping("/test")
    @ResponseBody
    public String getNameById(String id){
        MongoDatabase db1 = mongoClient.getDatabase("admin");
        MongoDatabase db2 = mongoClient.getDatabase("xxx");

//        UpdateResult updateResult = db1.getCollection("test").updateOne(Filters.eq("age", 5), Updates.set("age", 3));
        Document res = db1.getCollection("test").find(new Document("age", 3)).first();

        final ClientSession clientSession = mongoClient.startSession();
        TransactionOptions txnOpinions = TransactionOptions.builder()
                .readPreference(ReadPreference.primary())
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY).build();
        TransactionBody txnBody = new TransactionBody() {
            @Override
            public Object execute() {
                MongoCollection<Document> test1 = db1.getCollection("test").withWriteConcern(WriteConcern.MAJORITY);
                MongoCollection<Document> test2 = db2.getCollection("test").withWriteConcern(WriteConcern.MAJORITY);

                test1.insertOne(clientSession, new Document().append("_id", 2).append("age", 2));
                int i = 1/0;
                test2.insertOne(clientSession, new Document().append("_id", new ObjectId()).append("age", 3));

                return null;
            }
        };

        try{
            clientSession.startTransaction();
            MongoCollection<Document> test1 = db1.getCollection("test");
            MongoCollection<Document> test2 = db2.getCollection("test");

            test1.insertOne(clientSession, new Document().append("_id", 2).append("age", 2));
            int i = 1/0;
            test2.insertOne(clientSession, new Document().append("_id", new ObjectId()).append("age", 3));
//            clientSession.withTransaction(txnBody, txnOpinions);
            clientSession.commitTransaction();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            clientSession.close();
        }
        return  "111";
    }
}

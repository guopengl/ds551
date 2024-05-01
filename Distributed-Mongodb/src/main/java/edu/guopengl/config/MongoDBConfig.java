package edu.guopengl.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MongoDBConfig {

    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create("mongodb://127.0.0.1:27017/");
    }
}

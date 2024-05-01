package edu.guopengl.utils;

import edu.guopengl.config.ClusterConfig;
import edu.guopengl.params.TableParams;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

public class HashUtil {

    //always called with lock, collectionName always exists
    public static int getHashIndex(String collectionName, Document data){
        int numDataBases = ClusterConfig.getNumDatabases();
        String partitionKey = ClusterConfig.getTablesInfo().get(collectionName).getPartitionKey();
        Object val = data.get(partitionKey);
        if(val == null){
            return -1;
//            throw new Exception("data must has partitionKey field.");
        }

        if(val instanceof String){
            String str = (String) val;
            return str.hashCode() & (numDataBases - 1); //same as mod when numDataBases = 2^n
        }
        if(val instanceof Integer){
            Integer i = (Integer) val;
            return i & (numDataBases - 1); //same as mod when numDataBases = 2^n
        }

        return -1;
//        throw new Exception("unsupported partitionValue type.");

    }

}

package edu.guopengl.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.guopengl.utils.HttpUtil;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseMapper {

    public void addRecord(String userName, String gameName, double price) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "purchase_record");

        JSONObject record = new JSONObject();
        record.put("userName", userName);
        record.put("gameName", gameName);
        record.put("price", price);

        JSONArray data = new JSONArray();
        data.add(record);
        body.put("data", data);

        JSONObject res = HttpUtil.post("http://localhost:8080/insertMany", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
    }

    public JSONArray findByUserNameAndGameName(String userName, String gameName) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "purchase_record");

        JSONObject query = new JSONObject();
        query.put("userName", userName);
        query.put("gameName", gameName);
        body.put("query", query);

        JSONObject res = HttpUtil.post("http://localhost:8080/find", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
        return res.getJSONArray("data");
    }

    public JSONArray findByUserName(String userName) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "purchase_record");

        JSONObject query = new JSONObject();
        query.put("userName", userName);
        body.put("query", query);

        JSONObject res = HttpUtil.post("http://localhost:8080/find", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
        return res.getJSONArray("data");
    }

    public void deleteByUserNameAndGameName(String userName, String gameName) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "purchase_record");

        JSONObject query = new JSONObject();
        query.put("userName", userName);
        query.put("gameName", gameName);
        body.put("query", query);

        JSONObject res = HttpUtil.post("http://localhost:8080/deleteOne", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
    }
}

package edu.guopengl.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.guopengl.entity.User;
import edu.guopengl.utils.HttpUtil;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapper {
    public User findByNameAndPassword(String name, String password){
        JSONObject body = new JSONObject();
        body.put("collectionName", "user");

        JSONObject query = new JSONObject();
        query.put("name", name);
        query.put("password", password);
        body.put("query", query);

        JSONArray data = HttpUtil.post("http://localhost:8080/find", body).getJSONArray("data");
        if(data.size() == 0){
            return null;
        }
        JSONObject userData = data.getJSONObject(0);
        return new User(userData.getString("name"), userData.getString("password"),
                userData.getInteger("role"), userData.getDouble("balance"));
    }

    public User findByName(String name){
        JSONObject body = new JSONObject();
        body.put("collectionName", "user");

        JSONObject query = new JSONObject();
        query.put("name", name);
        body.put("query", query);

        JSONArray data = HttpUtil.post("http://localhost:8080/find", body).getJSONArray("data");
        if(data.size() == 0){
            return null;
        }
        JSONObject userData = data.getJSONObject(0);
        return new User(userData.getString("name"), userData.getString("password"),
                userData.getInteger("role"), userData.getDouble("balance"));
    }

    public void addUser(String username, String password) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "user");

        JSONObject user = new JSONObject();
        user.put("name", username);
        user.put("password", password);
        user.put("role", 1);
        user.put("balance", 100.0);

        JSONArray data = new JSONArray();
        data.add(user);
        body.put("data", data);

        JSONObject res = HttpUtil.post("http://localhost:8080/insertMany", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }

    }

    public void updateByBalance(String name, double newBalance) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "user");

        JSONObject query = new JSONObject();
        query.put("name", name);
        body.put("query", query);

        JSONObject update = new JSONObject();
        update.put("balance", newBalance);
        body.put("update", update);

        JSONObject res = HttpUtil.post("http://localhost:8080/updateOne", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
    }
}

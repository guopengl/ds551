package edu.guopengl.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.guopengl.utils.HttpUtil;
import org.springframework.stereotype.Repository;

@Repository
public class GameMapper {

    public JSONArray findAll() throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "game");

        JSONObject query = new JSONObject();

        body.put("query", query); //empty filter

        JSONObject res = HttpUtil.post("http://localhost:8080/find", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
        return res.getJSONArray("data");
    }
    public JSONArray findByName(String name) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "game");

        JSONObject query = new JSONObject();
        query.put("name", name);
        body.put("query", query); //empty filter

        JSONObject res = HttpUtil.post("http://localhost:8080/find", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
        return res.getJSONArray("data");
    }

    public void addGame(String name, String platform, String publisher, String developer, double criticScore, double userScore, int year, double price) throws Exception {
        JSONObject body = new JSONObject();
        body.put("collectionName", "game");

        JSONObject game = new JSONObject();
        game.put("name", name);
        game.put("platform", platform);
        game.put("publisher", publisher);
        game.put("developer", developer);
        game.put("criticScore", criticScore);
        game.put("userScore", userScore);
        game.put("year", year);
        game.put("price", price);

        JSONArray data = new JSONArray();
        data.add(game);
        body.put("data", data);

        JSONObject res = HttpUtil.post("http://localhost:8080/insertMany", body);
        if(res.getBoolean("err")){
            throw new Exception(res.getString("message"));
        }
    }
}

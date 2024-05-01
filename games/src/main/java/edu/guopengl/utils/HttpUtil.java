package edu.guopengl.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {
    private static CloseableHttpClient client = HttpClients.createDefault();
    public static JSONObject get(String url){
        JSONObject res = null;

        HttpGet httpGet = new HttpGet(url);
        try{
            HttpResponse httpResponse = client.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if(entity != null){
                String response = EntityUtils.toString(entity, "UTF-8");
                res = JSONObject.parseObject(response);
            }
        } catch (Exception e){

        }
        httpGet.releaseConnection();
        return res;
    }

    public static JSONObject post(String url, JSONObject body){
        JSONObject res = null;

        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(body.toString(), "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);

        try{
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if(entity != null){
                String response = EntityUtils.toString(entity, "UTF-8");
                res = JSONObject.parseObject(response);
            }
        } catch (Exception e){

        }

        httpPost.releaseConnection();
        return res;
    }
}

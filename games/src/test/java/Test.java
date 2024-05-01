import com.alibaba.fastjson.JSONObject;
import edu.guopengl.utils.HttpUtil;

public class Test {

    public static void main(String[] args) {
        JSONObject resquestParams = new JSONObject();
        resquestParams.put("collectionName", "user");
        JSONObject query = new JSONObject();
        query.put("name", "admin");
        query.put("password", "123");
        resquestParams.put("query", query);

        JSONObject response = HttpUtil.post("http://localhost:8080/find", resquestParams);
    }
}

package edu.guopengl.service;

import com.alibaba.fastjson.JSONArray;
import edu.guopengl.entity.User;
import edu.guopengl.mapper.GameMapper;
import edu.guopengl.mapper.LikeMapper;
import edu.guopengl.mapper.PurchaseMapper;
import edu.guopengl.mapper.UserMapper;
import edu.guopengl.params.AddGameParams;
import edu.guopengl.params.LikeParams;
import edu.guopengl.params.PurchaseParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private UserMapper userMapper;
    public JSONArray findAll() throws Exception {
        return gameMapper.findAll();
    }

    public void addGame(AddGameParams request) throws Exception {
        JSONArray data = gameMapper.findByName(request.getName());
        if(data.size() > 0){
            throw new Exception("game already exits.");
        }
        gameMapper.addGame(request.getName(), request.getPlatform(),
                request.getPublisher(), request.getDeveloper(), request.getCriticScore(), request.getUserScore(),
                request.getYear(), request.getPrice());
    }

    public void purchase(PurchaseParams request) throws Exception {
        JSONArray data = purchaseMapper.findByUserNameAndGameName(request.getUserName(), request.getGameName());
        if(data.size() > 0){
            throw new Exception("you have already purchased the game.");
        }

        User user = userMapper.findByName(request.getUserName());
        if(user.getBalance() < request.getPrice()){
            throw new Exception("balance not enough.");
        }
        purchaseMapper.addRecord(request.getUserName(), request.getGameName(), request.getPrice());
        //update balance of user
        userMapper.updateByBalance(user.getName(), user.getBalance() - request.getPrice());
    }

    public void like(LikeParams request) throws Exception {
        JSONArray data = likeMapper.findByUserNameAndGameName(request.getUserName(), request.getGameName());
        if(data.size() > 0){
            throw new Exception("you have already liked the game.");
        }
        likeMapper.addRecord(request.getUserName(), request.getGameName());
    }
}

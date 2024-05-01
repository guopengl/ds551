package edu.guopengl.service;

import com.alibaba.fastjson.JSONArray;
import edu.guopengl.entity.User;
import edu.guopengl.mapper.PurchaseMapper;
import edu.guopengl.mapper.UserMapper;
import edu.guopengl.params.PurchaseDeleteParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private UserMapper userMapper;
    public JSONArray findByName(String name) throws Exception {
        return purchaseMapper.findByUserName(name);
    }

    public void delete(PurchaseDeleteParams request) throws Exception {
        User user = userMapper.findByName(request.getUserName());
        purchaseMapper.deleteByUserNameAndGameName(request.getUserName(), request.getGameName());
        userMapper.updateByBalance(user.getName(), user.getBalance() + request.getPrice());
    }
}

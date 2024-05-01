package edu.guopengl.service;

import com.alibaba.fastjson.JSONArray;
import edu.guopengl.mapper.LikeMapper;
import edu.guopengl.params.LikeDeleteParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeMapper likeMapper;
    public JSONArray findAll() throws Exception {
        return likeMapper.findAll();
    }

    public void delete(LikeDeleteParams likeDeleteParams) throws Exception {
        likeMapper.deleteByUserNameAndGameName(likeDeleteParams.getUserName(), likeDeleteParams.getGameName());
    }

    public JSONArray findByName(String name) throws Exception {
        return likeMapper.findByUserName(name);
    }
}

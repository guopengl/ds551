package edu.guopengl.service;

import edu.guopengl.entity.User;
import edu.guopengl.mapper.UserMapper;
import edu.guopengl.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findByName(String name){
        return userMapper.findByName(name);
    }
    public User findByNameAndPassword(LoginParams request){
        return userMapper.findByNameAndPassword(request.getUsername(), request.getPassword());
    }


    public void addUser(LoginParams request) throws Exception {
        User user = userMapper.findByName(request.getUsername());
        if(user != null){
            throw new Exception("user with username '" + request.getUsername() + "' already exits.");
        }
        userMapper.addUser(request.getUsername(), request.getPassword());
    }
}

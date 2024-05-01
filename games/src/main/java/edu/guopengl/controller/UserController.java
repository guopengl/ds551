package edu.guopengl.controller;

import com.alibaba.fastjson.JSONObject;
import edu.guopengl.controller.response.Response;
import edu.guopengl.entity.User;
import edu.guopengl.params.LoginParams;
import edu.guopengl.service.UserService;
import edu.guopengl.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody LoginParams request, HttpSession httpSession){
        Response res =  new Response();
        try{
            User user = userService.findByNameAndPassword(request);
            if(user == null){
                res.setErr(true);
                res.setMessage("username or password wrong");
                return res;
            }
            String token = JWTUtil.generateJWT(user);
            httpSession.setAttribute("token", token);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @GetMapping("/getName")
    public Response getName(HttpSession session){
        Response res = new Response();

        String token = (String) session.getAttribute("token");
        Claims claims = JWTUtil.checkJWT(token);
        String name = claims.getSubject();
        res.setData(name);
        return res;
    }

    @GetMapping("/getBalance")
    public Response getBalance(HttpSession session){
        Response res = new Response();

        String token = (String) session.getAttribute("token");
        Claims claims = JWTUtil.checkJWT(token);
        String name = claims.getSubject();

        try{
            User user = userService.findByName(name);
            if(user == null){
                res.setErr(true);
                res.setMessage("username or password wrong");
                return res;
            }
            res.setData(user.getBalance());
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/register")
    public Response register(@RequestBody LoginParams request){
        Response res = new Response();
        try{
            userService.addUser(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @GetMapping("/logout")
    public Response logout(HttpSession session){
        Response res = new Response();
        session.removeAttribute("token");
        return res;
    }

}

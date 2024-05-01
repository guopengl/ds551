package edu.guopengl.controller;

import edu.guopengl.controller.response.Response;
import edu.guopengl.params.LikeDeleteParams;
import edu.guopengl.service.LikeService;
import edu.guopengl.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @GetMapping("/find")
    public Response find(HttpSession session){
        Response res = new Response();
        try{
            String token = (String) session.getAttribute("token");
            Claims claims = JWTUtil.checkJWT(token);
            String name = claims.getSubject();
            res.setData(likeService.findByName(name));
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody LikeDeleteParams likeDeleteParams, HttpSession session){
        Response res = new Response();
        try{
            String token = (String) session.getAttribute("token");
            Claims claims = JWTUtil.checkJWT(token);
            String name = claims.getSubject();
            likeDeleteParams.setUserName(name);

            likeService.delete(likeDeleteParams);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}

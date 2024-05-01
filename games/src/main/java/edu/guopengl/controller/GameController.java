package edu.guopengl.controller;

import edu.guopengl.controller.response.Response;
import edu.guopengl.entity.User;
import edu.guopengl.params.AddGameParams;
import edu.guopengl.params.LikeParams;
import edu.guopengl.params.LoginParams;
import edu.guopengl.params.PurchaseParams;
import edu.guopengl.service.GameService;
import edu.guopengl.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/findAll")
    public Response findAll(){
        Response res = new Response();
        try{
            res.setData(gameService.findAll());
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/add")
    public Response add(@RequestBody AddGameParams request){
        Response res =  new Response();
        try{
            gameService.addGame(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/purchase")
    public Response purchase(@RequestBody PurchaseParams request, HttpSession session){
        Response res =  new Response();
        try{
            String token = (String) session.getAttribute("token");
            Claims claims = JWTUtil.checkJWT(token);
            String name = claims.getSubject();
            request.setUserName(name);

            gameService.purchase(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/like")
    public Response like(@RequestBody LikeParams request, HttpSession session){
        Response res =  new Response();
        try{
            String token = (String) session.getAttribute("token");
            Claims claims = JWTUtil.checkJWT(token);
            String name = claims.getSubject();
            request.setUserName(name);

            gameService.like(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

}

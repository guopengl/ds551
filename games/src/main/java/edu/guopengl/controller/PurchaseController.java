package edu.guopengl.controller;

import edu.guopengl.controller.response.Response;
import edu.guopengl.params.LikeDeleteParams;
import edu.guopengl.params.PurchaseDeleteParams;
import edu.guopengl.service.LikeService;
import edu.guopengl.service.PurchaseService;
import edu.guopengl.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/find")
    public Response find(HttpSession session){
        Response res = new Response();
        try{
            String token = (String) session.getAttribute("token");
            Claims claims = JWTUtil.checkJWT(token);
            String name = claims.getSubject();
            res.setData(purchaseService.findByName(name));
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody PurchaseDeleteParams request, HttpSession session){
        Response res = new Response();
        try{
            String token = (String) session.getAttribute("token");
            Claims claims = JWTUtil.checkJWT(token);
            String name = claims.getSubject();
            request.setUserName(name);

            purchaseService.delete(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}

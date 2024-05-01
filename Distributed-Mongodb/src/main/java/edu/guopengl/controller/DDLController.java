package edu.guopengl.controller;

import com.alibaba.fastjson.JSONObject;
import edu.guopengl.controller.response.Response;
import edu.guopengl.params.CreateCollectionParams;
import edu.guopengl.params.DropCollectionParams;
import edu.guopengl.service.DDLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DDLController {
    @Autowired
    private DDLService ddlService;

    @GetMapping("/showClusters")
    public Response showClusters(){
        Response res = new Response();
        try{
            res.setData(ddlService.showClusters());
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }
    @GetMapping("/showTables")
    public Response showTables(){
        Response res = new Response();
        try{
            res.setData(ddlService.showTables());
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/createCollection")
    public Response createCollection(@RequestBody CreateCollectionParams request){
        Response res = new Response();
        try{
            boolean success = ddlService.createCollection(request);
            if(!success){
                res.setErr(true);
                res.setMessage("collection '" + request.getCollectionName() + "' already exists.");
            }
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/dropCollection")
    public Response dropCollection(@RequestBody DropCollectionParams request){
        Response res = new Response();
        try{
            boolean success = ddlService.dropCollection(request);
            if(!success){
                res.setErr(true);
                res.setMessage("collection '" + request.getCollectionName() + "' does not exist.");
            }
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}

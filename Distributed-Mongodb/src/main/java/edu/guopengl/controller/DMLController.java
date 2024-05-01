package edu.guopengl.controller;

import edu.guopengl.controller.response.Response;
import edu.guopengl.params.DeleteOneParams;
import edu.guopengl.params.FindAllParams;
import edu.guopengl.params.InsertManyParams;
import edu.guopengl.params.UpdateOneParams;
import edu.guopengl.service.DDLService;
import edu.guopengl.service.DMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMLController {
    @Autowired
    private DMLService dmlService;

    @PostMapping("/insertMany")
    public Response insertMany(@RequestBody InsertManyParams request){
        Response res = new Response();
        try{
            dmlService.insertMany(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/updateOne")
    public Response updateOne(@RequestBody UpdateOneParams request){
        Response res = new Response();
        try{
           dmlService.updateOne(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/deleteOne")
    public Response deleteOne(@RequestBody DeleteOneParams request){
        Response res = new Response();
        try{
            dmlService.deleteOne(request);
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @PostMapping("/find")
    public Response find(@RequestBody FindAllParams request){
        Response res = new Response();
        try{
            res.setData(dmlService.find(request));
        } catch (Exception e){
            res.setErr(true);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}

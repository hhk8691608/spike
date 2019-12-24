package com.ace.study.spike.api;


import com.ace.study.spike.Service.IndexService;
import com.ace.study.spike.VO.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/index")
@ResponseBody
public class IndexController {

    @Autowired
    private IndexService indexService;


    @GetMapping(path = "/testGet")
    public Map<String,Object> testGet(@RequestParam(defaultValue = "pig") String userName)
    {
        Map<String,Object> result = new HashMap<>();
        result.put("code","1");
        result.put("msg","success");
        result.put("userName",userName);
        return result;
    }


    @PostMapping(path = "/testPost")
    public Map<String,Object> testPost(@RequestBody IndexVO indexVO)
    {
        Map<String,Object> result = new HashMap<>();
        result.put("code","1");
        result.put("msg","success");
        result.put("indexVO",indexVO);
        return result;
    }



    @GetMapping(path = "/select2Db")
    public Map<String,Object> select2Db(@RequestParam Integer id)
    {
        Map<String,Object> result = new HashMap<>();
        result.put("code","1");
        result.put("msg","success");
        IndexVO indexVO = indexService.select2Db(id);
        result.put("indexVO",indexVO);
        return result;
    }



    @PostMapping(path = "/insert2Db")
    public Map<String,Object> insert2Db(@RequestBody IndexVO indexVO)
    {
        int code = indexService.insert2Db(indexVO);
        Map<String,Object> result = new HashMap<>();
        result.put("code",code);
        result.put("msg","success");
        return result;
    }


    @PostMapping(path = "/update2Db")
    public Map<String,Object> update2Db(@RequestBody IndexVO indexVO)
    {
        int code = indexService.update2Db(indexVO);
        Map<String,Object> result = new HashMap<>();
        result.put("code",1);
        result.put("msg","success");
        result.put("userName",code);
        return result;
    }




    @GetMapping(path = "/delete2Db")
    public Map<String,Object> delete2Db(@RequestParam Integer id)
    {
        int code = indexService.delete2Db(id);
        Map<String,Object> result = new HashMap<>();
        result.put("code",code);
        result.put("msg","success");
        return result;
    }


}

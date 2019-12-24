package com.ace.study.spike.api;


import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
import com.ace.study.spike.Controller.threadPool.ThreadPoolService;
import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.DO.OrderDO;
import com.ace.study.spike.Service.IndexService;
import com.ace.study.spike.VO.IndexVO;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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


    @Autowired
    private InventoryMapper inventoryMapper;


    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private OrderDispatcher orderDispatcher;

    @GetMapping(path = "/test")
    public Map<String,Object> test(@RequestParam String id,@RequestParam Integer version) throws InterruptedException {
//        InventoryDO inventoryDO = new InventoryDO();
//        inventoryDO.setGoodId(Long.parseLong(id) );
//        inventoryDO.setVersion(version);
//        int code = inventoryMapper.updateInventory(inventoryDO);


//        OrderDO orderDO = new OrderDO();
//        orderDO.setGoodId(Integer.parseInt(id) );
//        orderDO.setCreateTime(new Date());
//        orderDO.setOrderNum(new Date().getTime()+"");
//        orderDO.setPrice(""+new Date().getHours());
//        orderDO.setCreateBy(""+new Date().getHours());
//        int code = orderMapper.saveOrder(orderDO);
//
//        Map<String,Object> result = new HashMap<>();
//        result.put("code",code);
//        result.put("msg","success");
//        return result;

        WareHouse wareHouse = ThreadPoolService.getWareHouse();
        Product product = new Product();
        product.setInventoryId(Integer.parseInt(id) );
        product.setVersion(version);
        product.setOrderNum(new Date().getTime()+"");
        product.setPrice(""+new Date().getHours());
        product.setOrder2User(""+new Date().getHours());
        wareHouse.push(product);

        orderDispatcher.dispatch(wareHouse);
        Map<String,Object> result = new HashMap<>();
        result.put("msg","success");
        return result;
    }



}

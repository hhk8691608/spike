package com.ace.study.spike.Controller.threadPool;

import com.ace.study.spike.Controller.threadPool.service.ThreadService;
import com.ace.study.spike.VO.IndexVO;
import com.ace.study.spike.VO.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/***
 *
 *@Author Mark
 *@Date 2019/12/24 10 19
 *@Desciption
 * 采用线程池+消费者生产者模式
 */
@Controller
@RequestMapping(path = "/threadPool")
@ResponseBody
public class ThreadPoolConroller {


    @Autowired
    private ThreadService threadService;

    /*
     * @Author Ace
     * @Description
     * 获取库存量
     * @Date 2019/12/24 13:36
     * @Param []
     * @return void
     **/
    @GetMapping(path = "/getInventory")
    public Map<String,Object> getInventory(@RequestParam int id){
        Map<String,Object> result = threadService.getInventory(id);
        return result;
    }




    /*
     * @Author Ace
     * @Description
     * 预下单
     * @Date 2019/12/24 13:36
     * @Param []
     * @return void
     **/
    @PostMapping(path = "/preOrder")
    public Map<String,Object> preOrder(@RequestBody OrderVO orderVO){
        Map<String,Object> result =  threadService.preOrder(orderVO);
        return result;
    }


    /*
     * @Author Ace
     * @Description
     * 完成支付订单
     * @Date 2019/12/24 13:37
     * @Param []
     * @return void
     **/
    @PostMapping(path = "/payOrder")
    public Map<String,Object>  payOrder(@RequestBody OrderVO orderVO){
        Map<String,Object> result = threadService.payOrder(orderVO);
        return result;
    }


}

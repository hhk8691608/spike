package com.ace.study.spike.Controller.mom;

import com.ace.study.spike.Controller.Cache.ThreadCacheService;
import com.ace.study.spike.VO.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/***
 *
 *@Author Mark
 *@Date 2019/12/30 14 52
 *@Desciption
 * 消息中间件的引入
 */

@Controller
@RequestMapping(path = "/mom")
@ResponseBody
public class MOMController {


    @Autowired
    private MOMService momService;



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
        Map<String,Object> result = momService.getInventory(id);
        return result;
    }




    /*
     * @Author Ace
     * @Description
     * 预下单
     * 采用预扣库存的方式
     * 预扣库存实际就是“下单减库存”和 “付款减库存”两种方式的结合，将两次操作进行了前后关联，下单时预扣库存，付款时释放库存。
     * 下单后一般都有个 “有效付款时间”，超过该时间订单自动释放，这就是典型的预扣库存方案。
     * @Date 2019/12/24 13:36
     * @Param []
     * @return void
     **/
    @PostMapping(path = "/preOrder")
    public Map<String,Object> preOrder(@RequestBody OrderVO orderVO){
        Map<String,Object> result = momService.preOrder(orderVO);
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
        Map<String,Object> result = momService.payOrder(orderVO);
        return result;
    }






}

package com.ace.study.spike.Controller.threadPool.service;

import com.ace.study.spike.Controller.threadPool.Consumer.Consumer;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.VO.OrderVO;
import com.ace.study.spike.mapper.IndexMapper;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/***
 *@Author Mark
 *@Date 2019/12/24 10 52
 *@Desciption
 */
@Service
public class ThreadService {


    @Autowired
    private IndexMapper indexMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private OrderMapper orderMapper;

    public Map<String,Object> getInventory(int id){
        Map<String,Object> result = new HashMap<>();
        InventoryDO inventoryDO = inventoryMapper.getInventory(id);
        result.put("inventoryNum",inventoryDO.getGoodNum());
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
    public int preOrder(OrderVO orderVO){

        int code = 0;
        Product product = new Product();
        return code;
    }

    /*
     * @Author Ace
     * @Description
     * 完成下单
     * @Date 2019/12/24 13:37
     * @Param []
     * @return void
    **/
    public void finishOrder(OrderVO orderVO){

    }

    /*
     * @Author Ace
     * @Description
     * 完成支付订单
     * @Date 2019/12/24 13:37
     * @Param []
     * @return void
    **/
    public void payOrder(OrderVO orderVO){

    }


}

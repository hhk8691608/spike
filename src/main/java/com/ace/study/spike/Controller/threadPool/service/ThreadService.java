package com.ace.study.spike.Controller.threadPool.service;

import com.ace.study.spike.Controller.threadPool.Consumer.Consumer;
import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
import com.ace.study.spike.Controller.threadPool.ThreadPoolService;
import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.DO.OrderDO;
import com.ace.study.spike.DTO.EntryDto;
import com.ace.study.spike.VO.OrderVO;
import com.ace.study.spike.mapper.IndexMapper;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import com.ace.study.spike.utls.DateUtils;
import com.ace.study.spike.utls.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/***
 *@Author Mark
 *@Date 2019/12/24 10 52
 *@Desciption
 */
@Service
public class ThreadService {

    HashMap<String,Date> cacheTokenMap = new HashMap<>();



    @Autowired
    private IndexMapper indexMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDispatcher orderDispatcher;

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
    public Map<String,Object> preOrder(OrderVO orderVO){
        Map<String,Object> result = new HashMap<>();
        String token = null;
        try {
            InventoryDO inventory = inventoryMapper.getInventory(Integer.parseInt( orderVO.getId() +""));
            Long goodNum = inventory.getGoodNum();
            if(goodNum<=0){
                result.put("flag",300);
                result.put("msg","库存不足");
                return result;
            }
            int version = inventory.getVersion();
            Product product = new Product();
            product.setOrder2User(orderVO.getUserName());
            product.setOrderNum(OrderUtils.generateOrderNumber("A"));
            product.setVersion(Integer.parseInt((version+"")));
            product.setInventoryId( Integer.parseInt( orderVO.getId() +"")  );
            product.setPrice(orderVO.getPrice());
            ExecutorService pool = ThreadPoolService.getThreadPool();
            WareHouse wareHouse = ThreadPoolService.getWareHouse();
            wareHouse.getQueues().put(product);
            wareHouse.setOrderDispatcher(orderDispatcher);
            Consumer consumer = new Consumer();
            consumer.setWareHouse(wareHouse);
            pool.submit(consumer);

            token = OrderUtils.makeToken();
            cacheTokenMap.put(token,new Date());
            result.put("flag",200);
            result.put("token",token);
            result.put("msg","success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
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
    public Map<String,Object> payOrder(OrderVO orderVO){

        Map<String,Object> result = new HashMap<>();
        String token = orderVO.getToken();
        System.out.println("payOrder token = "+token);
        Date date1 = cacheTokenMap.get(token);
        Date date2 = new Date();

        long seconds = DateUtils.dateDiffMin(date1, date2);
        System.out.println("payOrder seconds = "+seconds);
        OrderDO orderDO = null;
        if(seconds >= 10){
            result.put("flag",300);
            result.put("msg","令牌过期");
            //回退库存
            orderDO = orderMapper.getOrderInfo( Integer.parseInt(orderVO.getId()+""));
            InventoryDO inventory = inventoryMapper.getInventory(Integer.parseInt( orderDO.getGoodId() +""));
            int code = inventoryMapper.addInventory(inventory);
            return result;
        }
        orderDO = orderMapper.getOrderInfo( Integer.parseInt(orderVO.getId()+""));
        if(orderDO == null){
            result.put("flag",300);
            result.put("msg","预订单不存在");
            return result;
        }
        orderDO.setVersion(OrderDispatcher.ORDER_COMMIT);
        int code = orderMapper.updateOrderByVersion(orderDO);
        //除掉cache的token
        cacheTokenMap.remove(token);
        //TODO 支付流水，账号扣费

        return result;
    }


}

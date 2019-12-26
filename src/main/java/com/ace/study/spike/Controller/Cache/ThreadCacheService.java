package com.ace.study.spike.Controller.Cache;

import com.ace.study.spike.Controller.Cache.Const.CacheConst;
import com.ace.study.spike.Controller.threadPool.Consumer.Consumer;
import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
import com.ace.study.spike.Controller.threadPool.ThreadPoolService;
import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.DO.OrderDO;
import com.ace.study.spike.VO.OrderVO;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import com.ace.study.spike.utls.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service("threadCacheService")
public class ThreadCacheService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDispatcher orderDispatcher;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private OrderMapper orderMapper;



    public int getInventory(int id){
        Map<String,Object> result = new HashMap<>();
        int goodNum = (int)redisTemplate.opsForValue().get(CacheConst.GOOD_UP_DAY+id);
        return goodNum;
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
        int code = 0;
        try {

            int goodNum = (int) redisTemplate.opsForValue().get(CacheConst.GOOD_UP_DAY+orderVO.getId());
            if(goodNum<=0){
                throw new Exception("库存不足");
            }
            OrderDO orderDO = new OrderDO();
            orderDO.setGoodId(Integer.parseInt( orderVO.getId()+"") );
            orderDO.setCreateTime(new Date());
            orderDO.setOrderNum(OrderUtils.generateOrderNumber());
            orderDO.setPrice(orderVO.getPrice());
            orderDO.setCreateBy(orderVO.getUserName());
            orderDO.setVersion(OrderDispatcher.ORDER_PRE_COMMIT);
            code = orderMapper.saveOrder(orderDO);
            if(code == 0){
                result.put("code",300);
                return result;
            }
            //预扣库存
            redisTemplate.opsForValue().set( (CacheConst.GOOD_UP_DAY+orderVO.getId()), (goodNum - 1) );
            //支付令牌
            String  token = OrderUtils.makeToken();
            redisTemplate.opsForValue().set( (token+":"+orderVO.getId() ),token);
            redisTemplate.expire(token, 10, TimeUnit.MINUTES);
            result.put("token",token);
            result.put("code",code);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public Map<String,Object> payOrder(OrderVO orderVO) {

        Map<String,Object> result = new HashMap<>();
        result.put("code", 300);
        String token = orderVO.getToken();
        String cacheToken = (String) redisTemplate.opsForValue().get( (token+":"+orderVO.getGoodId() ));
        if(cacheToken == null || !cacheToken.equals(token)){
            result.put("msg", "令牌过期");
            return result;
        }
        InventoryDO inventory = inventoryMapper.getInventory(Integer.parseInt( orderVO.getGoodId() +""));
        Long goodNum = inventory.getGoodNum();
        if(goodNum<=0) {
            result.put("msg", "库存不足");
            return result;
        }
        int version = inventory.getVersion();
        System.out.println("goodNum="+goodNum+" ,version="+version);
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setGoodId(Long.parseLong(orderVO.getGoodId()+"") );
        inventoryDO.setVersion(version);
        int code = inventoryMapper.updateInventory(inventoryDO);
        if(code == 0){
            result.put("msg", "库存不足");
            return result;
        }
        OrderDO orderDO = orderMapper.getOrderInfo( Integer.parseInt(orderVO.getId()+""));
        if(orderDO == null){
            result.put("msg", "订单不存在");
            return result;
        }
        orderDO.setVersion(OrderDispatcher.ORDER_COMMIT);
        code = orderMapper.updateOrderByVersion(orderDO);
        if(code == 0){
            //TODO 支付失败回退库存


            //除掉cache的token
            redisTemplate.delete(  (token+":"+orderVO.getGoodId() ) );
            result.put("msg", "支付失败");
            return result;
        }
        //除掉cache的token
        redisTemplate.delete(  (token+":"+orderVO.getGoodId() ) );
        result.put("code",200);
        result.put("msg", "完成");
        return result;
    }
}

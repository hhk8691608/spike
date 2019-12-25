package com.ace.study.spike.Controller.Cache;

import com.ace.study.spike.Controller.Cache.Const.CacheConst;
import com.ace.study.spike.Controller.threadPool.Consumer.Consumer;
import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
import com.ace.study.spike.Controller.threadPool.ThreadPoolService;
import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.VO.OrderVO;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import com.ace.study.spike.utls.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public int preOrder(OrderVO orderVO){
        int code = 0;
        try {
            InventoryDO inventory = inventoryMapper.getInventory(Integer.parseInt( orderVO.getId() +""));
            Long goodNum = inventory.getGoodNum();
            if(goodNum<=0){
                throw new Exception("库存不足");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }












}

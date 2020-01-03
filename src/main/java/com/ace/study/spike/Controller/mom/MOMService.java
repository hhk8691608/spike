package com.ace.study.spike.Controller.mom;

import com.ace.study.spike.Const.SystemConst;
import com.ace.study.spike.Const.business.OrderConst;
import com.ace.study.spike.Controller.Cache.Const.CacheConst;
import com.ace.study.spike.Controller.mom.message.product.KafkaProducer;
import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
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

@Service("momService")
public class MOMService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private OrderMapper orderMapper;


    public Map<String,Object> getInventory(int id) {
        Map<String,Object> result = new HashMap<>();
        int goodNum = (int)redisTemplate.opsForValue().get(CacheConst.GOOD_UP_DAY+id);
        result.put("goodNum", goodNum);
        result.put("flag", SystemConst.RESPONSE_FLAG_SUCEESS);
        result.put("msg", SystemConst.RESPONSE_MSG_SUCEESS);
        return result;
    }

    public Map<String,Object> preOrder(OrderVO orderVO) {

        Map<String,Object> result = new HashMap<>();
        int code = 0;
        int goodNum = (int) redisTemplate.opsForValue().get(CacheConst.GOOD_UP_DAY+orderVO.getId());
        if(goodNum<=0){
            result.put("flag", SystemConst.RESPONSE_FLAG_ERROR);
            result.put("msg", OrderConst.INVENTORY_SHORTAGE_);
            return result;
        }
        OrderDO orderDO = new OrderDO();
        orderDO.setGoodId(Integer.parseInt( orderVO.getId()+"") );
        orderDO.setCreateTime(new Date());
        String orderNum = OrderUtils.generateOrderNumber();
        orderDO.setOrderNum(orderNum);
        orderDO.setPrice(orderVO.getPrice());
        orderDO.setCreateBy(orderVO.getUserName());
        orderDO.setVersion(OrderDispatcher.ORDER_PRE_COMMIT);
        code = orderMapper.saveOrder(orderDO);

        //预扣库存
        redisTemplate.opsForValue().set( (CacheConst.GOOD_UP_DAY+orderVO.getId()), (goodNum - 1) );
        kafkaProducer.sendOrderMessage(orderVO);
        //TODO 生成令牌  String  token = OrderUtils.makeToken();
        result.put("orderNum",orderNum);
        result.put("flag", SystemConst.RESPONSE_FLAG_SUCEESS);
        result.put("msg", SystemConst.RESPONSE_MSG_SUCEESS);
        return result;
    }

    public Map<String,Object> payOrder(OrderVO orderVO) {

        Map<String,Object> result = new HashMap<>();
        InventoryDO inventory = inventoryMapper.getInventory(Integer.parseInt( orderVO.getGoodId() +""));
        Long goodNum = inventory.getGoodNum();
        if(goodNum<=0) {
            result.put("msg", OrderConst.INVENTORY_SHORTAGE_);
            return result;
        }

        OrderDO orderDO = orderMapper.getOrderInfoByOrderNum( orderVO.getOrderNum());
        if(orderDO == null){
            result.put("msg", OrderConst.ORDER_NOT_EXIST);
            return result;
        }
        orderDO.setVersion(OrderDispatcher.ORDER_COMMIT);
        int code = orderMapper.updateOrderByVersion(orderDO);
        if(code == 0){
            result.put("msg", OrderConst.PAYMENT_FAILED);
            return result;
        }
        int version = inventory.getVersion();
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setGoodId(Long.parseLong(orderVO.getGoodId()+"") );
        inventoryDO.setVersion(version);
        code = inventoryMapper.updateInventory(inventoryDO);
        if(code == 0){
            result.put("msg", OrderConst.PAYMENT_FAILED);
            return result;
        }

        //除掉cache的token
        result.put("flag", SystemConst.RESPONSE_FLAG_SUCEESS);
        result.put("msg", SystemConst.RESPONSE_MSG_SUCEESS);
        return result;
    }

}

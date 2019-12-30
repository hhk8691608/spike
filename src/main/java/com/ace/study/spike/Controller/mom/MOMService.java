package com.ace.study.spike.Controller.mom;

import com.ace.study.spike.Const.SystemConst;
import com.ace.study.spike.Controller.Cache.Const.CacheConst;
import com.ace.study.spike.Controller.mom.message.product.KafkaProducer;
import com.ace.study.spike.VO.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("momService")
public class MOMService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;


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
        kafkaProducer.sendOrderMessage(orderVO);
        //TODO 生成令牌
        result.put("flag", SystemConst.RESPONSE_FLAG_SUCEESS);
        result.put("msg", SystemConst.RESPONSE_MSG_SUCEESS);
        return result;
    }

    public Map<String,Object> payOrder(OrderVO orderVO) {
        Map<String,Object> result = new HashMap<>();
        return result;
    }

}

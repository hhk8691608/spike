package com.ace.study.spike.Controller.mom.message.consumer;

import com.ace.study.spike.VO.OrderVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/***
 *
 *@Author Mark
 *@Date 2019/12/30 15 21
 *@Desciption
 *  kafka消息的消费者
 */
@Component
public class KafkaConsumer {

//    测试环境
//    @KafkaListener(topics = {"app_log"})
//    public void receive(String message){
//        System.out.println("app_log--消费消息:" + message);
//    }
//
//    @KafkaListener(topics = {"app_log2"})
//    public void receive2(String message){
//        System.out.println("app_log2--消费消息:" + message);
//    }


    @KafkaListener(topics = {"app_log"})
    public void receive(String message){
//        OrderVO orderVO = (OrderVO) JSONObject.parse(message);
        System.out.println("app_log--消费消息:message=" + message);
        OrderVO orderVO =  JSONObject.parseObject(message,OrderVO.class);
        System.out.println("app_log--消费消息:" + orderVO);
    }

}

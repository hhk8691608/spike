package com.ace.study.spike.Controller.mom.message.product;


import java.util.Date;
import java.util.UUID;

import com.ace.study.spike.Const.SystemConst;
import com.ace.study.spike.VO.OrderVO;
import com.ace.study.spike.VO.mom.KafkaVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;



@Component
@EnableScheduling
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    测试环境
//    /**
//     * 定时任务
//     */
//    @Scheduled(cron = "00/1 * * * * ?")
//    public void send(){
//        String message = UUID.randomUUID().toString();
//        KafkaVO vo = new KafkaVO();
//        vo.setId(message);
//        vo.setMsg("需要发送消息的内容");
//        vo.setDate(new Date());
//        ListenableFuture<?> future = kafkaTemplate.send("app_log",JSONObject.toJSONString(vo));
//        String message2 = "第二种类的订阅消息发送";
//        ListenableFuture<?> future2 = kafkaTemplate.send("app_log2",message2);
//        future.addCallback(o -> System.out.println("send-消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));
//        future2.addCallback(o -> System.out.println("send-消息发送成功：" + message2), throwable -> System.out.println("消息发送失败：" + message2));
//    }

    public void sendOrderMessage(OrderVO orderVO){

        String message = UUID.randomUUID().toString();
        orderVO.setMessage(message);
        orderVO.setMsg(SystemConst.MQ_PRE_ORDER_MSG);
        orderVO.setDate(new Date());
        ListenableFuture<?> future = kafkaTemplate.send("app_log",JSONObject.toJSONString(orderVO));
        future.addCallback(o -> System.out.println("预下单-消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));

    }


}

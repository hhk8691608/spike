package com.ace.study.spike.Controller.threadPool.Middleware;

import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.DO.OrderDO;
import com.ace.study.spike.DTO.EntryDto;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

@Service
public class OrderDispatcher {


    @Autowired
    private  OrderMapper orderMapper;

    @Autowired
    private  InventoryMapper inventoryMapper;

    public int dispatch(WareHouse wareHouse) throws InterruptedException {
            int code = 0;
            BlockingQueue<Product> queues =  wareHouse.getQueues();
            Product product = wareHouse.pop();
            int version = product.getVersion();
            int inventoryId = product.getInventoryId();
            InventoryDO inventoryDO = new InventoryDO();
            inventoryDO.setGoodId(Long.parseLong(inventoryId+"") );
            inventoryDO.setVersion(version);
            code = inventoryMapper.updateInventory(inventoryDO);
            if(code == 0){
                return code;
            }
            OrderDO orderDO = new OrderDO();
            orderDO.setGoodId(inventoryId);
            orderDO.setCreateTime(new Date());
            orderDO.setOrderNum(product.getOrderNum());
            orderDO.setPrice(product.getPrice());
            orderDO.setCreateBy(product.getOrder2User());
            code = orderMapper.saveOrder(orderDO);
            System.out.println("call end 。。。。。。。");
            return code;
    }

}

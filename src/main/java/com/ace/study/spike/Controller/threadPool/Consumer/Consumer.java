package com.ace.study.spike.Controller.threadPool.Consumer;

import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import com.ace.study.spike.DO.InventoryDO;
import com.ace.study.spike.DO.OrderDO;
import com.ace.study.spike.DTO.EntryDto;
import com.ace.study.spike.mapper.InventoryMapper;
import com.ace.study.spike.mapper.OrderMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/***
 *
 *@Author Mark
 *@Date 2019/12/24 10 24
 *@Desciption
 */
public class Consumer implements Runnable {


    @Getter
    @Setter
    private WareHouse wareHouse;



    public Consumer(){}

    public Consumer(WareHouse wareHouse){
        this.wareHouse = wareHouse;
    }

    @Override
    public void run() {
        OrderDispatcher orderDispatcher = wareHouse.getOrderDispatcher();
        try {
            int code =  orderDispatcher.dispatch(wareHouse);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

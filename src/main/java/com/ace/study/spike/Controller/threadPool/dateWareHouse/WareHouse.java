package com.ace.study.spike.Controller.threadPool.dateWareHouse;

import com.ace.study.spike.Controller.threadPool.Middleware.OrderDispatcher;
import com.ace.study.spike.Controller.threadPool.prouduct.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/***
 *
 *@Author Mark
 *@Date 2019/12/24 10 32
 *@Desciption
 * 数据仓库
 */
public class WareHouse {

    public WareHouse(){}
    public WareHouse(OrderDispatcher orderDispatcher){
        this.orderDispatcher = orderDispatcher;
    }

    @Getter
    @Setter
    private OrderDispatcher orderDispatcher;

    private BlockingQueue<Product> queues = new LinkedBlockingQueue<Product>();

    public Product pop() throws InterruptedException {
        if(queues.size() == 0){
            return null;
        }
        return queues.take();
    }

    public void push(Product p) throws InterruptedException {
        synchronized (queues){
            queues.put(p);
        }
    }

    public BlockingQueue<Product> getQueues() {
        return queues;
    }

    public void setQueues(BlockingQueue<Product> queues) {
        this.queues = queues;
    }


}

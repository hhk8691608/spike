package com.ace.study.spike.Controller.threadPool.Consumer;

import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import lombok.Getter;
import lombok.Setter;

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



    }



}

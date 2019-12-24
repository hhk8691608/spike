package com.ace.study.spike.Controller.threadPool.prouduct;

import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/***
 *
 *@Author Mark
 *@Date 2019/12/24 10 33
 *@Desciption
 * 生产者
 */
public class Product implements Runnable {

    @Getter
    @Setter
    private WareHouse wareHouse;

    public Product(){}

    public Product(WareHouse wareHouse){
        this.wareHouse = wareHouse;
    }


    @Override
    public void run() {

    }
}

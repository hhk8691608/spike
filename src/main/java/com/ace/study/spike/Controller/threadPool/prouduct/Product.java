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
@Data
public class Product {

    private String orderNum;
    private int version;
    private int inventoryId;
    private String price;
    private String order2User;


    public Product(){}


}

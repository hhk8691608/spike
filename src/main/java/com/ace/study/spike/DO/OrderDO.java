package com.ace.study.spike.DO;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDO {

    private int id;
    private int goodId;
    private String orderNum;
    private String price;
    private Date createTime;
    private String createBy;


}

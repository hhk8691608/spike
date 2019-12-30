package com.ace.study.spike.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVO implements Serializable {

    private Long id;
    private String token;
    private Date tokenTime;
    private String price;
    private String userName;
    private Long goodId;
    private String orderNum;

    //MQ字段
    private String message;
    private String msg;
    private Date date;


    @Override
    public String toString() {
        return "OrderVO{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", tokenTime=" + tokenTime +
                ", price='" + price + '\'' +
                ", userName='" + userName + '\'' +
                ", goodId=" + goodId +
                ", orderNum='" + orderNum + '\'' +
                ", message='" + message + '\'' +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}

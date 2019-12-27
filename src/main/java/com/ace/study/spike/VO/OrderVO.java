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

}

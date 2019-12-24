package com.ace.study.spike.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVO implements Serializable {

    private Long id;
    private String token;
    private Date tokenTime;



}

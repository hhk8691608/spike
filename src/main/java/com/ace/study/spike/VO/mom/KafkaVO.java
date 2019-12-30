package com.ace.study.spike.VO.mom;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 *
 *@Author Mark
 *@Date 2019/12/30 15 23
 *@Desciption
 */
@Data
public class KafkaVO implements Serializable {

    private String id;
    private String message;
    private String msg;
    private Date date;

}

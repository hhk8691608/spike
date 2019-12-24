package com.ace.study.spike.DO;

import com.ace.study.spike.VO.IndexVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class IndexDO implements Serializable {

    private Long id;
    private String dsc;

    public IndexDO(){}

    public IndexDO(IndexVO indexVO){
        this.id = indexVO.getId();
        this.dsc = indexVO.getName();
    }

}

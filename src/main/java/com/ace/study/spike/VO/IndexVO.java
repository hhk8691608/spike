package com.ace.study.spike.VO;

import com.ace.study.spike.DO.IndexDO;
import lombok.Data;

import java.io.Serializable;


@Data
public class IndexVO implements Serializable {

    private Long id ;
    private String name;
    private String pwd;

    public IndexVO(){}

    public IndexVO(IndexDO indexDO){
        this.id = indexDO.getId();
        this.name = indexDO.getDsc();
    }


}

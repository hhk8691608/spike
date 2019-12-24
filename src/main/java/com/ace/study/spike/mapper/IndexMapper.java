package com.ace.study.spike.mapper;

import com.ace.study.spike.DO.IndexDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface IndexMapper {


    public IndexDO select2Db(Integer id);

    public int insert2Db(IndexDO indexDO);

    public int update2Db(IndexDO indexDO);

    public int delete2Db(Integer id);

}

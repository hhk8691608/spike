package com.ace.study.spike.mapper;

import com.ace.study.spike.DO.OrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    public OrderDO getOrderInfo(int id);

    public int saveOrder(OrderDO orderDO);

}

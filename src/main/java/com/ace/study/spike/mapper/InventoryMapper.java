package com.ace.study.spike.mapper;


import com.ace.study.spike.DO.InventoryDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryMapper {

    public InventoryDO getInventory(Integer id);

    public int updateInventory(InventoryDO inventoryDO);
}

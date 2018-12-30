package com.itheima.core.dao.item;

import com.itheima.core.pojo.item.ItemCatCheck;
import com.itheima.core.pojo.item.ItemCatCheckQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCatCheckDao {
    int countByExample(ItemCatCheckQuery example);

    int deleteByExample(ItemCatCheckQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(ItemCatCheck record);

    int insertSelective(ItemCatCheck record);

    List<ItemCatCheck> selectByExample(ItemCatCheckQuery example);

    ItemCatCheck selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ItemCatCheck record, @Param("example") ItemCatCheckQuery example);

    int updateByExample(@Param("record") ItemCatCheck record, @Param("example") ItemCatCheckQuery example);

    int updateByPrimaryKeySelective(ItemCatCheck record);

    int updateByPrimaryKey(ItemCatCheck record);
}
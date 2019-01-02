package com.itheima.core.dao.item;

import com.itheima.core.pojo.item.ItemCat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyItemCatDao {
    List<ItemCat> findItemCatListByParentId(@Param("parentId") Long parentId);
}

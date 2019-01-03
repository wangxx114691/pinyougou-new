package com.itheima.core;

import com.itheima.core.pojo.item.ItemCat;
import com.itheima.core.pojo.item.ItemCatCheck;
import entity.PageResult;

import java.util.List;

public interface ItemCatCheckService {
    List<ItemCatCheck> findByParentId(Long parentId, String name);

    PageResult search(Integer page, Integer rows, Long parentId, String name);

    void add(ItemCatCheck itemCat, String name);

    void update(ItemCatCheck itemCat);

    void delete(Long[] ids);

    ItemCatCheck findOne(Long id);

    List<ItemCatCheck> findAll();

    PageResult search(Integer page, Integer rows);

    void updateStatus(Long[] ids, String status);
}

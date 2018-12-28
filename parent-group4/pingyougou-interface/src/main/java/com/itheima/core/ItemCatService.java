package com.itheima.core;

import com.itheima.core.pojo.item.ItemCat;
import entity.PageResult;

import java.util.List;

public interface ItemCatService {
    List<ItemCat> findByParentId(Long parentId);

    PageResult search(Integer page, Integer rows, Long parentId);

    void add(ItemCat itemCat);

    void update(ItemCat itemCat);

    void delete(Long[] ids);

    ItemCat findOne(Long id);

    List<ItemCat> findAll();
}

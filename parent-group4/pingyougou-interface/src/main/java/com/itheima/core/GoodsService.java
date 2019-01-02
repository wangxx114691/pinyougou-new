package com.itheima.core;

import com.itheima.core.pojo.good.Goods;
import entity.PageResult;
import pojogroup.GoodsVo;

public interface GoodsService {
    void add(GoodsVo vo);

    PageResult search(Integer page, Integer rows, Goods goods);

    void update(GoodsVo vo);

    GoodsVo findOne(Long id);

    void updateStatus(Long[] ids, String status);

    void delete(Long[] ids);

    void downdele(Long[] ids);

    void marketableStatus(Long[] ids, String marketable);

}

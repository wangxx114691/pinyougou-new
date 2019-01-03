package com.itheima.core;

import com.itheima.core.pojo.good.BrandCheck;
import entity.PageResult;

public interface BrandCheckService {

    void add(BrandCheck brandCheck, String name);

    PageResult<BrandCheck> search(Integer pageNum, Integer pageSize, BrandCheck brandCheck, String name);

    BrandCheck findOne(Long id);

    PageResult search(Integer page, Integer rows);

    void updateStatus(Long[] ids, String status);
}

package com.itheima.core;

import com.itheima.core.pojo.good.Brand;
import entity.PageResult;
import entity.Result;

import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<Brand> findAll();

    PageResult<Brand> findPage(Integer pageNum, Integer pageSize);

    void add(Brand brand);

    boolean update(Brand brand);

    Brand findOne(Long id);

    boolean delete(Long[] ids);

    PageResult<Brand> search(Integer pageNum, Integer pageSize, Brand brand);

    List<Map> selectOptionList();
}

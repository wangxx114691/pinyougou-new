package com.itheima.core;

import com.itheima.core.pojo.specification.SpecificationCheck;
import entity.PageResult;
import pojogroup.SpecificationVo;

import java.util.List;
import java.util.Map;

public interface SpecCheckService {
    void add(SpecificationVo specificationVo, String name);

    SpecificationVo findOne(Long id);

    void update(SpecificationVo specificationVo);

    void delete(Long[] ids);

    PageResult search(Integer page, Integer rows, SpecificationCheck specificationCheck, String name);

    List<Map> selectOptionList(String name);

}

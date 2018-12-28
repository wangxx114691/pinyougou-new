package com.itheima.core;

import com.itheima.core.pojo.specification.Specification;
import com.itheima.core.pojo.template.TypeTemplate;
import entity.PageResult;
import entity.Result;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate);

    void add(TypeTemplate typeTemplate);

    void update(TypeTemplate typeTemplate);

    TypeTemplate findOne(Long id);

    void delete(Long[] ids);

    List<Map> selectOptionList();

    List<Map> findBySpecList(Long id);
}

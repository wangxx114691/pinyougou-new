package com.itheima.core;

import com.itheima.core.pojo.template.TypeTemplate;
import com.itheima.core.pojo.template.TypeTemplateCheck;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeCheckService {

    PageResult search(Integer page, Integer rows, TypeTemplateCheck typeTemplateCheck);

    void add(TypeTemplateCheck typeTemplate);

    void update(TypeTemplateCheck typeTemplate);

    TypeTemplateCheck findOne(Long id);

    List<Map> findBySpecList(Long id);

    void delete(Long[] ids);

    List<Map> selectOptionList();
}

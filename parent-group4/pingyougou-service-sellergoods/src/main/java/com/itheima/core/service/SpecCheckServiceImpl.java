package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.SpecCheckService;
import com.itheima.core.dao.specification.SpecificationDao;
import com.itheima.core.dao.specification.SpecificationOptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pojogroup.SpecificationVo;

@Service
@Transactional
public class SpecCheckServiceImpl implements SpecCheckService{

    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Override
    public void add(SpecificationVo specificationVo, String name) {

    }
}

package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.itheima.core.SpecificationCheckService;
import com.itheima.core.dao.specification.SpecificationCheckDao;
import com.itheima.core.pojo.specification.SpecificationCheck;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SpecificationCheckServiceImpl implements SpecificationCheckService {
    @Autowired
    private SpecificationCheckDao specificationCheckDao;

    @Override
    public PageResult search(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Page<SpecificationCheck> p= (Page<SpecificationCheck>) specificationCheckDao.selectByExample(null);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        SpecificationCheck specificationCheck = new SpecificationCheck();
        specificationCheck.setCheckStatus(status);
        for (Long id : ids) {
            specificationCheck.setId(id);
            specificationCheckDao.updateByPrimaryKeySelective(specificationCheck);
        }
    }

}

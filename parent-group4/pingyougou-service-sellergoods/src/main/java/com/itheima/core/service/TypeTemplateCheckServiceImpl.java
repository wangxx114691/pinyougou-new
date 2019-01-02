package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.itheima.core.TypeTemplateCheckService;
import com.itheima.core.dao.template.TypeTemplateCheckDao;
import com.itheima.core.pojo.template.TypeTemplateCheck;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TypeTemplateCheckServiceImpl implements TypeTemplateCheckService {
   @Autowired
private TypeTemplateCheckDao typeTemplateCheckDao;
    @Override
    public PageResult search(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Page<TypeTemplateCheck> p= (Page<TypeTemplateCheck>) typeTemplateCheckDao.selectByExample(null);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        TypeTemplateCheck typeTemplateCheck = new TypeTemplateCheck();
        typeTemplateCheck.setCheckStatus(status);
        for (Long id : ids) {
            typeTemplateCheck.setId(id);
            typeTemplateCheckDao.updateByPrimaryKeySelective(typeTemplateCheck);
        }

    }
}

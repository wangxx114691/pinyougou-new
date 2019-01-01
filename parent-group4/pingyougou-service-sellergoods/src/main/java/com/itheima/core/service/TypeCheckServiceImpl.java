package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.TypeCheckService;
import com.itheima.core.TypeTemplateService;
import com.itheima.core.dao.seller.SellerDao;
import com.itheima.core.dao.specification.SpecificationOptionDao;
import com.itheima.core.dao.template.TypeTemplateCheckDao;
import com.itheima.core.dao.template.TypeTemplateDao;
import com.itheima.core.pojo.seller.Seller;
import com.itheima.core.pojo.specification.SpecificationOption;
import com.itheima.core.pojo.specification.SpecificationOptionQuery;
import com.itheima.core.pojo.template.TypeTemplate;
import com.itheima.core.pojo.template.TypeTemplateCheck;
import com.itheima.core.pojo.template.TypeTemplateCheckQuery;
import com.itheima.core.pojo.template.TypeTemplateQuery;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TypeCheckServiceImpl implements TypeCheckService {

    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private TypeTemplateCheckDao typeTemplateCheckDao;

    @Override
    public void add(TypeTemplateCheck typeTemplate) {
        Seller seller = sellerDao.selectByPrimaryKey(typeTemplate.getSellerId());
        typeTemplate.setSellerName(seller.getName());// 封装商户名
        typeTemplate.setCheckStatus("0");
        typeTemplateCheckDao.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplateCheck typeTemplate) {
        typeTemplateCheckDao.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public TypeTemplateCheck findOne(Long id) {
        return typeTemplateCheckDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        TypeTemplateCheckQuery query = new TypeTemplateCheckQuery();
        query.createCriteria().andIdIn(Arrays.asList(ids));
        typeTemplateCheckDao.deleteByExample(query);
    }

    @Override
    public List<Map> selectOptionList() {
        return typeTemplateCheckDao.selectOptionList();
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        TypeTemplateCheck typeTemplate = typeTemplateCheckDao.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();
        // 封装List<Map>
        List<Map> mapList = JSON.parseArray(specIds, Map.class);    // 后面的参数决定返回什么泛型
        for (Map map : mapList) {
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            query.createCriteria().andSpecIdEqualTo((long)(Integer)map.get("id"));
            List<SpecificationOption> optionList = specificationOptionDao.selectByExample(query);
            map.put("options",optionList);
        }
        return mapList;
    }

    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplateCheck typeTemplateCheck) {

        // 分页助手
        PageHelper.startPage(page, rows);
        TypeTemplateCheckQuery query = new TypeTemplateCheckQuery();
        query.setOrderByClause("id desc");  // 设置主键降序, 这样对于新添加的选项会出现在第一页比较方便
        TypeTemplateCheckQuery.Criteria criteria = query.createCriteria().andSellerIdEqualTo(typeTemplateCheck.getSellerId());
        if (typeTemplateCheck.getName() != null && !"".equals(typeTemplateCheck.getName().trim())) {
            criteria.andNameLike("%" + typeTemplateCheck.getName().trim() + "%");
        }
        Page<TypeTemplateCheck> p = (Page<TypeTemplateCheck>) typeTemplateCheckDao.selectByExample(query);
        return new PageResult(p.getTotal(), p.getResult());
    }
}

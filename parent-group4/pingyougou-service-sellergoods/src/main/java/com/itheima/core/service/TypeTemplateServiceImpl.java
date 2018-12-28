package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.TypeTemplateService;
import com.itheima.core.dao.specification.SpecificationDao;
import com.itheima.core.dao.specification.SpecificationOptionDao;
import com.itheima.core.dao.template.TypeTemplateDao;
import com.itheima.core.pojo.specification.Specification;
import com.itheima.core.pojo.specification.SpecificationOption;
import com.itheima.core.pojo.specification.SpecificationOptionQuery;
import com.itheima.core.pojo.template.TypeTemplate;
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
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate) {
        // 1.查询mysql，并保存到缓冲中，之后访问redis就行了
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(null);
        for (TypeTemplate template : typeTemplates) {
            String brandIds = template.getBrandIds();
            List<Map> brandList = JSON.parseArray(brandIds, Map.class);
            redisTemplate.boundHashOps("brandList").put(template.getId(),brandList);
            List<Map> specList = findBySpecList(template.getId());
            redisTemplate.boundHashOps("specList").put(template.getId(),specList);
        }

        // 分页助手
        PageHelper.startPage(page, rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        query.setOrderByClause("id desc");  // 设置主键降序, 这样对于新添加的选项会出现在第一页比较方便
        if (typeTemplate.getName() != null && !"".equals(typeTemplate.getName().trim())) {
            query.createCriteria().andNameLike("%" + typeTemplate.getName().trim() + "%");
        }
        Page<TypeTemplate> p = (Page<TypeTemplate>) typeTemplateDao.selectByExample(query);
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        TypeTemplateQuery query = new TypeTemplateQuery();
        query.createCriteria().andIdIn(Arrays.asList(ids));
        typeTemplateDao.deleteByExample(query);
    }

    @Override
    public List<Map> selectOptionList() {
        return typeTemplateDao.selectOptionList();
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
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
}

package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.SpecCheckService;
import com.itheima.core.dao.seller.SellerDao;
import com.itheima.core.dao.specification.SpecificationCheckDao;
import com.itheima.core.dao.specification.SpecificationOptionDao;
import com.itheima.core.pojo.seller.Seller;
import com.itheima.core.pojo.specification.*;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pojogroup.SpecificationVo;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpecCheckServiceImpl implements SpecCheckService {

    @Autowired
    private SpecificationCheckDao specificationCheckDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private SellerDao sellerDao;

    @Override
    public void add(SpecificationVo specificationVo, String name) {
        SpecificationCheck specificationCheck = specificationVo.getSpecificationCheck();
        specificationCheck.setSellerId(name);
        Seller seller = sellerDao.selectByPrimaryKey(name);
        specificationCheck.setSellerName(seller.getName());
        specificationCheckDao.insertSelective(specificationCheck);
        Long specId = specificationCheck.getId();
//        SpecificationOptionQuery query = new SpecificationOptionQuery();
//        SpecificationOptionQuery.Criteria criteria = query.createCriteria();
//        criteria.andIdIn(vo.getSpecificationOptionList().)
        List<SpecificationOption> specificationOptionList = specificationVo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            specificationOption.setSpecId(specId);
            specificationOptionDao.insertSelective(specificationOption);
        }
    }

    @Override
    public void update(SpecificationVo vo) {
//        SpecificationQuery query = new SpecificationQuery();
//        SpecificationQuery.Criteria queryCriteria = query.createCriteria();
//        if (null != vo.getSpecification() && !"".equals(vo.getSpecification().getSpecName().trim()))
//            queryCriteria.andSpecNameLike("%" + vo.getSpecification().getSpecName().trim() + "%");
        SpecificationCheck specificationCheck = vo.getSpecificationCheck();
        if (null != specificationCheck && !"".equals(specificationCheck.getName().trim())) {
            Long specId = specificationCheck.getId();
            // 首先更新主表的specName
            specificationCheck.setName(specificationCheck.getName().trim());
            specificationCheckDao.updateByPrimaryKey(specificationCheck);

            // 获取表单中的SpecificationOption集合
            List<SpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
            SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
            optionQuery.createCriteria().andSpecIdEqualTo(specId);
            // 删除数据中的原specId对应的每一条SpecificationOption记录
            specificationOptionDao.deleteByExample(optionQuery);
            // 更新从表中每条SpecificationOption记录
            for (SpecificationOption specificationOption : specificationOptionList) {
                specificationOption.setSpecId(specId);
                specificationOptionDao.insertSelective(specificationOption);
            }

        }
    }

    @Override
    public SpecificationVo findOne(Long id) {
        SpecificationVo vo = new SpecificationVo();
        SpecificationCheck specificationCheck = specificationCheckDao.selectByPrimaryKey(id);
        vo.setSpecificationCheck(specificationCheck);
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(id);
        // 增加根据orders排序的功能, 添加顺序可以改不了,这能通过查询的条件的限制来控制输出的顺序
        query.setOrderByClause("orders desc");  // 降序需要自己写, 因为默认是升序的(升序可以省略asc)
        List<SpecificationOption> specificationOptionList = specificationOptionDao.selectByExample(query);
        vo.setSpecificationOptionList(specificationOptionList);
        return vo;
    }


    /**
     * 总结起来就是,不管是删除还是修改都必须满足是先改变的是从表,也就是先改变一对多的中的多, 再回头过来改一
     *
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
//        SpecificationQuery specificationQuery = new SpecificationQuery();
//        SpecificationQuery.Criteria specCriteria = specificationQuery.createCriteria();
//        specCriteria.andIdIn(Arrays.asList(ids));
//        List<Specification> specificationList = specificationDao.selectByExample(specificationQuery);
//        for (int i = 0; i < specificationList.size(); i++) {
//            Specification specification = specificationList.get(i);
//            SpecificationOptionQuery query = new SpecificationOptionQuery();
//            SpecificationOptionQuery.Criteria criteria = query.createCriteria();
//            criteria.andSpecIdEqualTo(specification.getId());
//            specificationOptionDao.deleteByExample(query);
//            specificationDao.deleteByPrimaryKey(specification.getId());
//        }


        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            SpecificationOptionQuery.Criteria criteria = query.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionDao.deleteByExample(query);
            specificationCheckDao.deleteByPrimaryKey(id);
        }

    }

    @Override
    public PageResult search(Integer page, Integer rows, SpecificationCheck specificationCheck, String name) {

        PageHelper.startPage(page, rows);
        SpecificationCheckQuery query = new SpecificationCheckQuery();
        SpecificationCheckQuery.Criteria criteria = query.createCriteria().andSellerIdEqualTo(name);

        if (null != specificationCheck.getName() && !"".equals(specificationCheck.getName().trim()))
            criteria.andNameLike("%" + specificationCheck.getName().trim() + "%");
        Page<SpecificationCheck> p = (Page<SpecificationCheck>) specificationCheckDao.selectByExample(query);
//        Page<Specification> p = (Page<Specification>) specificationDao.selectByExample(null);     // 这个是无条件查询时使用的
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public List<Map> selectOptionList(String name) {
        return specificationCheckDao.selectOptionList(name);
    }


}

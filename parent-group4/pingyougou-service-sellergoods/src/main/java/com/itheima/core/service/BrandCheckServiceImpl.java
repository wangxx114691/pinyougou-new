package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.BrandCheckService;
import com.itheima.core.dao.good.BrandCheckDao;
import com.itheima.core.dao.good.BrandDao;
import com.itheima.core.dao.seller.SellerDao;
import com.itheima.core.pojo.good.Brand;
import com.itheima.core.pojo.good.BrandCheck;
import com.itheima.core.pojo.good.BrandCheckQuery;
import com.itheima.core.pojo.seller.Seller;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrandCheckServiceImpl implements BrandCheckService {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private BrandCheckDao brandCheckDao;
    @Autowired
    private SellerDao sellerDao;
    @Override
    public void add(BrandCheck brandCheck, String name) {
        Brand brand = new Brand();
        brand.setName(brandCheck.getName());
        brandDao.insertSelective(brand);
        brandCheck.setCheckStatus("0");
        brandCheck.setSellerId(name);
        Seller seller = sellerDao.selectByPrimaryKey(name);
        brandCheck.setSellerName(seller.getName());
        brandCheckDao.insertSelective(brandCheck);
    }

    @Override
    public PageResult<BrandCheck> search(Integer pageNum, Integer pageSize, BrandCheck brandCheck, String name) {

        PageHelper.startPage(pageNum,pageSize);
        BrandCheckQuery query = new BrandCheckQuery();
        BrandCheckQuery.Criteria criteria = query.createCriteria();
        criteria.andSellerIdEqualTo(name);
        if (null !=  brandCheck.getName() && !"".equals(brandCheck.getName().trim())){
            criteria.andNameLike("%"+brandCheck.getName().trim()+"%");
        }
        Page<BrandCheck> p = (Page<BrandCheck>) brandCheckDao.selectByExample(query);
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Override
    public BrandCheck findOne(Long id) {
        return brandCheckDao.selectByPrimaryKey(id);
    }
}

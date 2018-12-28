package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.SellerService;
import com.itheima.core.dao.seller.SellerDao;
import com.itheima.core.pojo.seller.Seller;
import com.itheima.core.pojo.seller.SellerQuery;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDao sellerDao;

    @Override
    public void add(Seller seller) {
        // 先给密码进行加密处理,使用spring自带的BCrypt进行加密
        seller.setPassword(new BCryptPasswordEncoder().encode(seller.getPassword()));
        seller.setStatus("0");
        sellerDao.insertSelective(seller);
    }

    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Seller seller) {
        // 分页控制
        PageHelper.startPage(pageNum,pageSize);
        SellerQuery query = new SellerQuery();
        SellerQuery.Criteria criteria = query.createCriteria();
        if (null!=seller.getName() && !"".equals(seller.getName().trim())){
            criteria.andNameLike("%"+seller.getName().trim()+"%");
        }
        if (null!=seller.getNickName() && !"".equals(seller.getNickName().trim())){
            criteria.andNickNameLike("%"+seller.getNickName().trim()+"%");
        }
        criteria.andStatusEqualTo(seller.getStatus());
        Page<Seller> p = (Page<Seller>) sellerDao.selectByExample(query);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public Seller findOne(String sellerId) {
        return sellerDao.selectByPrimaryKey(sellerId);
    }

    @Override
    public void updateStatus(String sellerId,String status) {
        Seller seller = new Seller();
        seller.setStatus(status);
        seller.setSellerId(sellerId);
        sellerDao.updateByPrimaryKeySelective(seller);
    }

    @Override
    public void delete(String[] ids) {
        SellerQuery query = new SellerQuery();
        query.createCriteria().andSellerIdIn(Arrays.asList(ids));
        sellerDao.deleteByExample(query);
    }


}

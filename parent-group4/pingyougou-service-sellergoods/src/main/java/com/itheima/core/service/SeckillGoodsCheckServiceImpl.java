package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.SeckillGoodsCheckService;
import com.itheima.core.dao.seckill.SeckillGoodsDao;
import com.itheima.core.pojo.seckill.SeckillGoods;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SeckillGoodsCheckServiceImpl implements SeckillGoodsCheckService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Override
    public PageResult search(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Page<SeckillGoods> p= (Page<SeckillGoods>) seckillGoodsDao.selectByExample(null);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setStatus(status);
        for (Long id : ids) {
            seckillGoods.setId(id);
            seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
        }

    }
}

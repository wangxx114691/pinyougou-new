package com.zero.chn.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zero.chn.core.dao.seckill.SeckillGoodsDao;
import com.zero.chn.core.dao.seckill.SeckillOrderDao;
import com.zero.chn.core.pojo.seckill.SeckillGoods;
import com.zero.chn.core.pojo.seckill.SeckillOrder;
import com.zero.chn.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

@Service
public class seckillOrderServiceImpl implements seckillOrderService {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void submitOrder(Long seckillId,String name) {



        SeckillGoods seckillGoods = seckillGoodsDao.selectByPrimaryKey(seckillId);

        SeckillGoods seckillGood = (SeckillGoods) redisTemplate.boundHashOps("seckillGood").get(seckillGoods.getId());
        seckillGood.setStockCount(seckillGood.getStockCount() -1);
        redisTemplate.boundHashOps("seckillGood").put(seckillGoods.getId(),seckillGood);


        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setSellerId(seckillGoods.getSellerId());
        seckillOrder.setMoney(seckillGoods.getCostPrice());
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setStatus("0");
        seckillOrder.setUserId(name);
        seckillOrder.setTransactionId(String.valueOf(idWorker.nextId()));

        seckillOrderDao.insertSelective(seckillOrder);

    }
}

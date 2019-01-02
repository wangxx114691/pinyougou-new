package com.zero.chn.core.task;

import com.zero.chn.core.dao.good.GoodsDescDao;
import com.zero.chn.core.dao.seckill.SeckillGoodsDao;
import com.zero.chn.core.pojo.seckill.SeckillGoods;
import com.zero.chn.core.pojo.seckill.SeckillGoodsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class seckillTask {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron="0 * * * * ?")//查询所有秒杀商品集合，间隔为每分钟
    public void refreshSeckillGoods(){
        System.out.println("执行了任务调度"+new Date());
        List seckillGoodIDs = new ArrayList(redisTemplate.boundHashOps("seckillGood").keys());
        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        criteria.andStatusEqualTo("1");
        criteria.andEndTimeGreaterThan(new Date());//秒杀结束时间大于当前时间，表示正在秒杀
        criteria.andStockCountGreaterThan(0);
        criteria.andStartTimeLessThan(new Date());//秒杀开始时间一定要小于当前时间
        if(seckillGoodIDs.size() > 0){
            criteria.andGoodsIdNotIn(seckillGoodIDs);
        }
        List<SeckillGoods> seckillGoodsList = seckillGoodsDao.selectByExample(seckillGoodsQuery);
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            redisTemplate.boundHashOps("seckillGood").put(seckillGoods.getId(),seckillGoods);
        }
        //当数据库中有数据的status为0时，需要立即从缓存中删除该商品
       // SeckillGoodsQuery goodsQuery = new SeckillGoodsQuery();
        List<SeckillGoods> seckillGoods = seckillGoodsDao.selectByExample(null);
        for (SeckillGoods seckillGood : seckillGoods) {
            if("0".equals(seckillGood.getStatus())){
                redisTemplate.boundHashOps("seckillGood").delete(seckillGood.getId());
            }
        }

    }

    @Scheduled(cron="* * * * * ?")   //每秒钟去执行一次，看看秒杀商品是否过期，过期的话从缓存中删除
    public void removeSeckillGoods(){
        System.out.println("检查秒杀商品活动中每件商品是否到期或者库存为0");
        List<SeckillGoods> seckillGoodList = redisTemplate.boundHashOps("seckillGood").values();
        for (SeckillGoods seckillGoods : seckillGoodList) {
            if(seckillGoods.getEndTime().getTime() <  new Date().getTime() || seckillGoods.getStockCount() <= 0){
                seckillGoods.setStatus("0");//表示不参加秒杀商品
                seckillGoodsDao.updateByPrimaryKey(seckillGoods);
                redisTemplate.boundHashOps("seckillGood").delete(seckillGoods.getId());
            }
        }
        System.out.println("秒杀商品活动结束");

    }




}

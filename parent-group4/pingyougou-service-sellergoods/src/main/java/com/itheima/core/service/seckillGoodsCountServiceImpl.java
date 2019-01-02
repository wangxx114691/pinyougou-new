package com.zero.chn.core.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.zero.chn.core.dao.seckill.SeckillOrderDao;
import com.zero.chn.core.pojo.seckill.SeckillOrder;
import com.zero.chn.core.pojo.seckill.SeckillOrderQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class seckillGoodsCountServiceImpl implements seckillGoodsCountService  {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Override
    public Map<String,Integer> countSeckillGoodsOrder(String name) {

        Map<String,Integer> map = new HashMap<>();

        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        seckillOrderQuery.createCriteria().andSellerIdEqualTo(name);
        List<SeckillOrder> seckillOrders = seckillOrderDao.selectByExample(seckillOrderQuery);
         map.put("orderCount",seckillOrders.size());
         int lastthreeday = 0 ;
         int ThreeAndSevenday = 0 ;
         int beforeThisWeek = 0;
        map.put("lastThreeDayCount",lastthreeday);
        map.put("threeToSevenDayCount",ThreeAndSevenday);
        map.put("beforeThisWeekCount",beforeThisWeek);
        Integer sum = 0;
        for (SeckillOrder seckillOrder : seckillOrders) {

            long time = new Date().getTime();
            if(seckillOrder.getCreateTime().getTime() >=(time-60*60*24*1000*3) && seckillOrder.getCreateTime().getTime() <= time ){
                lastthreeday ++;
                map.put("lastThreeDayCount",lastthreeday);
            }else if(seckillOrder.getCreateTime().getTime() >=(time-60*60*24*1000*7) && seckillOrder.getCreateTime().getTime() <(time-60*60*24*1000*3)){
                 ThreeAndSevenday ++;
                map.put("threeToSevenDayCount",ThreeAndSevenday);
            }else if(seckillOrder.getCreateTime().getTime() <(time -60*60*24*7)){
                beforeThisWeek++;
                map.put("beforeThisWeekCount",beforeThisWeek);
            }
            sum +=seckillOrder.getMoney().intValue();
        }
        map.put("OrderAmount",sum);
        return map;
    }

    @Override
    public void print() {
        System.out.println();
    }
}

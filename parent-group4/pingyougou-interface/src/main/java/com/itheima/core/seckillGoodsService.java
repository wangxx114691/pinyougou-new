package com.itheima.core;


import com.itheima.core.pojo.good.seckillGoodVO;
import com.itheima.core.pojo.seckill.SeckillGoods;

import java.util.List;

public interface seckillGoodsService {
    List<SeckillGoods> findSeckillGoods();

    seckillGoodVO findOneFromRedis(Long id);

    //SeckillGoods findOneFromRedis(Long id);


}

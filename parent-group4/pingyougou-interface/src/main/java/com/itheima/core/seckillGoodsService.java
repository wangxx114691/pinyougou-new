package com.zero.chn.core.service;

import com.zero.chn.PackagePojo.seckillGoodVO;
import com.zero.chn.core.pojo.seckill.SeckillGoods;

import java.util.List;

public interface seckillGoodsService {
    List<SeckillGoods> findSeckillGoods();

    seckillGoodVO findOneFromRedis(Long id);

    //SeckillGoods findOneFromRedis(Long id);


}

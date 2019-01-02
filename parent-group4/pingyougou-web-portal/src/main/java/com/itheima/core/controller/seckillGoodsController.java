package com.zero.chn.core.controller;


import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.zero.chn.PackagePojo.seckillGoodVO;
import com.zero.chn.core.pojo.seckill.SeckillGoods;
import com.zero.chn.core.service.seckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("seckillGoods")
public class seckillGoodsController {
    @Reference
    private seckillGoodsService seckillGoodsService;



   @RequestMapping("findList")
    public List<SeckillGoods> findSeckillGoods(){
       List<SeckillGoods> seckillGoodsList =seckillGoodsService.findSeckillGoods();

       return seckillGoodsList;
   }

  /* @RequestMapping("findOneFromRedis")
    public SeckillGoods findOneFromRedis(Long id){
      SeckillGoods seckillGoods =  seckillGoodsService.findOneFromRedis(id);
       return seckillGoods;
   }*/
  @RequestMapping("findOneFromRedis")
  public seckillGoodVO findOneFromRedis(Long id){
     seckillGoodVO seckillGoods =  seckillGoodsService.findOneFromRedis(id);
      return seckillGoods;
  }




}

package com.itheima.core.controller;



import com.alibaba.dubbo.config.annotation.Reference;


import com.itheima.core.pojo.good.seckillGoodVO;
import com.itheima.core.pojo.seckill.SeckillGoods;
import com.itheima.core.seckillGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("seckillGoods")
public class seckillGoodsController {
    @Reference
    private seckillGoodsService seckillGoodsService;



   @RequestMapping("findList")
    public List<SeckillGoods> findSeckillGoods(){
       List<SeckillGoods> seckillGoodsList = seckillGoodsService.findSeckillGoods();

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

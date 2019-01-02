package com.zero.chn.core.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zero.chn.core.service.seckillGoodsCountService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("seckillGoodsCount")
public class seckillGoodsCountController {
    @Reference
    private seckillGoodsCountService  seckillGoodsCountService;

    @RequestMapping("countSeckillGoodsOrder")
    private Map<String,Integer>  countSeckillGoodsOrder(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
          Map<String,Integer> map =  seckillGoodsCountService.countSeckillGoodsOrder(name);
        return  map;

    }
}

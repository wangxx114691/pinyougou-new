package com.zero.chn.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zero.chn.Utils.Result;
import com.zero.chn.core.pojo.seckill.SeckillOrder;
import com.zero.chn.core.service.seckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seckillOrder")
public class seckillOrderController {
    @Reference
    private seckillOrderService  seckillOrderService;

    @RequestMapping("submitOrder")
    private Result submitOrder(Long seckillId){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            seckillOrderService.submitOrder(seckillId,name);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(true,"成功");

    }

}

package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.OrdersShopService;
import com.itheima.core.pojo.order.Order;
import com.itheima.core.pojo.order.OrderItem;
import entity.PageResult;
import entity.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Reference
    private OrdersShopService ordersShopService;

    @RequestMapping("search")
    public PageResult<Order> search(Integer page, Integer rows, @RequestBody Order order){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setSellerId(name);
        return  ordersShopService.search(page,rows,order);
    }
    @RequestMapping("updateTime")
    public Date updateTime(@DateTimeFormat(pattern = "yyyy-MM-dd") Date time){
        System.out.println(time);
        return  time;
    }



}

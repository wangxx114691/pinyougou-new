package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.OrderCountService;
import entity.OrdersVo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("ordersCount")
public class OrderCountController {

    @Reference
    private OrderCountService orderCountService;

    private List<Date> dateList=new ArrayList<>();

    @RequestMapping("search")
    public OrdersVo searchList(Integer page,Integer rows,@RequestBody(required = false) OrdersVo ordersVo){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dateList.size()>0&&dateList!=null){
            ordersVo.setBeginTime(dateList.get(0));
            ordersVo.setOverTime(dateList.get(1));
        }
        OrdersVo search = orderCountService.search(page, rows, ordersVo, name);
        if (dateList.size()>0&&dateList!=null){
            dateList.clear();
        }
        return search;
    }
    @RequestMapping("updateTime")
    public Date updateTime(@DateTimeFormat(pattern = "yyyy-MM-dd") Date time){
        dateList.add(time);
        return time;
    }
}

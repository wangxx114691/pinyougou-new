package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.SeckillGoodsCheckService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillGoodsCheck")
public class SeckillGoodsCheckController {
    @Reference
    private SeckillGoodsCheckService seckillGoodsCheckService;
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows){
        return seckillGoodsCheckService.search(page,rows);
    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            seckillGoodsCheckService.updateStatus(ids,status);
            return new Result(true,"审核成功");
        } catch (Exception e) {
          //  e.printStackTrace();
            return new Result(true,"审核失败");
        }
    }
}

package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.core.BrandCheckService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/brandCheck")
public class BrandCheckController {
    @Reference
    private BrandCheckService brandCheckService;


    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows){
        return brandCheckService.search(page,rows);
    }

    //更新状态
    @RequestMapping("/updateStatus")
        public Result updateStatus(Long[] ids, String status){
            try {
                    brandCheckService.updateStatus(ids,status);
                    return new Result(true,"审核成功");
            } catch (Exception e) {
                   // e.printStackTrace();
                    return new Result(false,"审核失败");
            }
    }
}

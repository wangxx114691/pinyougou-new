package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.core.SpecificationCheckService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specificationCheck")
public class SpecificationCheckController {
    @Reference
    private SpecificationCheckService specificationCheckService;
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows){
        return specificationCheckService.search(page,rows);
    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            specificationCheckService.updateStatus(ids,status);
            return new Result(true,"审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"审核失败");
        }
    }
}

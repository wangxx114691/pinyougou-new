package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.BrandCheckService;
import com.itheima.core.pojo.good.Brand;
import com.itheima.core.pojo.good.BrandCheck;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brandCheck")
public class BrandCheckController {

    @Reference
    private BrandCheckService brandCheckService;

    @RequestMapping("add")
    public Result add(@RequestBody BrandCheck brandCheck){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            brandCheckService.add(brandCheck,name);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

    @RequestMapping("search")
//    public PageResult<Brand> search(Integer pageNum, Integer pageSize,@RequestBody(required = false) Brand brand){
    public PageResult<BrandCheck> search(Integer pageNum, Integer pageSize, @RequestBody BrandCheck brandCheck){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return brandCheckService.search(pageNum,pageSize,brandCheck,name);
    }


    @RequestMapping("findOne")
    public BrandCheck findOne(Long id){
        return brandCheckService.findOne(id);
    }
}

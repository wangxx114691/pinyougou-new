package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.core.ItemCatCheckService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemCatCheck")
public class ItemCatCheckController {
    @Reference
    private ItemCatCheckService itemCatCheckService;
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows){
        return itemCatCheckService.search(page,rows);
    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            itemCatCheckService.updateStatus(ids,status);
            return new Result(true,"审核成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(true,"审核失败");
        }
    }
}

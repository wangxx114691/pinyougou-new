package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.ItemCatCheckService;
import com.itheima.core.pojo.item.ItemCat;
import com.itheima.core.pojo.item.ItemCatCheck;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("itemCatCheck")
public class ItemCatCheckController {
    @Reference
    private ItemCatCheckService itemCatCheckService;
    @RequestMapping("findByParentId2")
    public List<ItemCatCheck> findByParentId2(Long parentId){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return itemCatCheckService.findByParentId(parentId,name);
    }

    @RequestMapping("findAll2")
    public List<ItemCatCheck> findAll2(){
        return itemCatCheckService.findAll();
    }

    @RequestMapping("findOne2")
    public ItemCatCheck findOne2(Long id){
        return itemCatCheckService.findOne(id);
    }
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            itemCatCheckService.delete(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }


    @RequestMapping("update")
    public Result update(@RequestBody ItemCatCheck itemCat){
        try {
            itemCatCheckService.update(itemCat);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("add")
    public Result add(@RequestBody ItemCatCheck itemCatCheck){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            itemCatCheckService.add(itemCatCheck,name);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }


}

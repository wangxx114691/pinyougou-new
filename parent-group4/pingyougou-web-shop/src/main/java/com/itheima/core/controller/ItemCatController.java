package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.ItemCatCheckService;
import com.itheima.core.ItemCatService;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.item.ItemCat;
import com.itheima.core.pojo.item.ItemCatCheck;
import com.itheima.core.pojo.specification.Specification;
import com.itheima.core.pojo.template.TypeTemplateCheck;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;


    /**
     * 先写通过父ID来查询子分类的信息, 这个方法在前端页面会被迭代调用,从而循环查出后面的其他二三级分类
     * @param parentId
     * @return
     */
    @RequestMapping("findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }




    @RequestMapping("findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }


    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, Long parentId){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return itemCatService.search(page,rows,parentId);
    }





}

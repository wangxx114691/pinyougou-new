package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.ItemCatCheckService;
import com.itheima.core.ItemCatService;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.item.ItemCat;
import com.itheima.core.pojo.item.ItemCatCheck;
import com.itheima.core.pojo.specification.Specification;
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
    @Reference
    private ItemCatCheckService itemCatCheckService;

    /**
     * 先写通过父ID来查询子分类的信息, 这个方法在前端页面会被迭代调用,从而循环查出后面的其他二三级分类
     * @param parentId
     * @return
     */
    @RequestMapping("findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

    @RequestMapping("findByParentId2")
    public List<ItemCatCheck> findByParentId2(Long parentId){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return itemCatCheckService.findByParentId(parentId,name);
    }
    @RequestMapping("findOne")
    public ItemCatCheck findOne(Long id){
        return itemCatCheckService.findOne(id);
    }

    @RequestMapping("findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }

    @RequestMapping("findAll2")
    public List<ItemCatCheck> findAll2(){
        return itemCatCheckService.findAll2();
    }
    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, Long parentId){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return itemCatCheckService.search(page,rows,parentId,name);
    }
    @RequestMapping("add")
    public Result add(@RequestBody ItemCat itemCat){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            itemCatCheckService.add(itemCat,name);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("update")
    public Result update(@RequestBody ItemCat itemCat){
        try {
            itemCatService.update(itemCat);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    /**
     * 由于这里的连个表itemCat与TypeTemplate没有进行外键关联,所以删除操作先进行简单 操作,后续要改再加强方法即可
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            itemCatService.delete(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }


}

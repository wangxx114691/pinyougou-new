package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.GoodsService;
import com.itheima.core.pojo.good.Goods;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojogroup.GoodsVo;

@RestController
@RequestMapping("goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;
    @RequestMapping("add")
    public Result add(@RequestBody GoodsVo vo){
        try {
            // 封装商家id进入vo, 好在Service层进行进步的封装
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            vo.getGoods().setSellerId(name);
            goodsService.add(vo);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    // 查询所有分页的商品
    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, @RequestBody Goods goods){
        // 运营商不需要进行用户判断, 所以直接可以查看所有用户的信息
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        goods.setSellerId(name);
        return goodsService.search(page,rows,goods);
    }

    @RequestMapping("findOne")
    public GoodsVo findOne(Long id){
        return goodsService.findOne(id);
    }

    @RequestMapping("update")
    public Result update(@RequestBody GoodsVo vo){
        // 封装商家id进入vo, 好在Service层进行进步的封装
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        vo.getGoods().setSellerId(name);
        try {
            goodsService.update(vo);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            goodsService.updateStatus(ids,status);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            goodsService.delete(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("downdele")
    public Result downdele(Long[] ids){
        try {
            goodsService.downdele(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }
    @RequestMapping("marketableStatus")
    public Result marketableStatus(Long[] ids,String marketable){
        try {
            goodsService.marketableStatus(ids,marketable);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }
}

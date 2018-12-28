package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.SellerService;
import com.itheima.core.pojo.seller.Seller;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("add")
    public Result add(@RequestBody Seller seller){
        try {
            sellerService.add(seller);
            return new Result(true,"申请成功,等待审核");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"申请失败,请重试");
        }
    }

    @RequestMapping("search")
    public PageResult search(Integer page,Integer rows,@RequestBody Seller seller){
        System.out.println("==========>进来了");
        return sellerService.search(page,rows,seller);

    }

    /**
     * 设置回显
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public Seller findOne(String id){
        return sellerService.findOne(id);
    }

    @RequestMapping("updateStatus")
    public Result updateStatus(String sellerId,String status){
        try {
            sellerService.updateStatus(sellerId,status);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("dele")
    public Result delete(String[] ids){
        try {
            sellerService.delete(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }

    }

}

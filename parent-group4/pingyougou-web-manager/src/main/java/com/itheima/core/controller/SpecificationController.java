package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.SpecificationService;
import com.itheima.core.pojo.specification.Specification;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojogroup.SpecificationVo;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;
    @RequestMapping("search")
    public PageResult search(Integer page,Integer rows,@RequestBody Specification specification){
        return specificationService.search(page,rows,specification);
    }

    @RequestMapping("add")
    public Result add(@RequestBody SpecificationVo specificationVo){
        Result result = null;
        try {
            specificationService.add(specificationVo);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }

    @RequestMapping("update")
    public Result update(@RequestBody SpecificationVo specificationVo){
        Result result = null;
        try {
            specificationService.update(specificationVo);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }
    @RequestMapping("findOne")
    public SpecificationVo findOne(Long id){
        return specificationService.findOne(id);
    }

    @RequestMapping("delete")
    public Result delete(Long[] ids){
        Result result = null;
        try {
            specificationService.delete(ids);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }

    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }
}

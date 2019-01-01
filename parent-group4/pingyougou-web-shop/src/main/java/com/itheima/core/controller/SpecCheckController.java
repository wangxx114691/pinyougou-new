package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.SpecCheckService;
import com.itheima.core.pojo.specification.Specification;
import com.itheima.core.pojo.specification.SpecificationCheck;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojogroup.SpecificationVo;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("specCheck")
public class SpecCheckController {

    @Reference
    private SpecCheckService specCheckService;

    @RequestMapping("add")
    public Result add(@RequestBody SpecificationVo specificationVo){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            specCheckService.add(specificationVo,name);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, @RequestBody SpecificationCheck specificationCheck){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return specCheckService.search(page,rows,specificationCheck,name);
    }

    @RequestMapping("findOne")
    public SpecificationVo findOne(Long id){
        return specCheckService.findOne(id);
    }

    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return specCheckService.selectOptionList(name);
    }

}

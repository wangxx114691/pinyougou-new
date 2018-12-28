package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.TypeTemplateService;
import com.itheima.core.pojo.template.TypeTemplate;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows,@RequestBody TypeTemplate typeTemplate){
        return typeTemplateService.search(page,rows,typeTemplate);
    }

    @RequestMapping("add")
    public Result add(@RequestBody TypeTemplate typeTemplate){
        Result result = null;
        try {
            typeTemplateService.add(typeTemplate);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }
    @RequestMapping("update")
    public Result update(@RequestBody TypeTemplate typeTemplate){
        Result result = null;
        try {
            typeTemplateService.update(typeTemplate);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }

    @RequestMapping("findOne")
    public TypeTemplate findOne(Long id){
        return typeTemplateService.findOne(id);
    }

    @RequestMapping("delete")
    public Result delete(Long[] ids){
        Result result = null;
        try {
            typeTemplateService.delete(ids);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"操作失败");
        }
        return result;
    }

    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        return typeTemplateService.selectOptionList();
    }
}

package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.TypeCheckService;
import com.itheima.core.pojo.template.TypeTemplateCheck;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("typeCheck")
public class TypeCheckController {

    @Reference
    private TypeCheckService typeCheckService;

    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, @RequestBody TypeTemplateCheck typeTemplateCheck) {
        if (typeTemplateCheck == null) {
            typeTemplateCheck = new TypeTemplateCheck();
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        typeTemplateCheck.setSellerId(name);
        return typeCheckService.search(page, rows, typeTemplateCheck);
    }

    @RequestMapping("findAll")
    public PageResult findAll(Integer page, Integer rows, @RequestBody(required = false) TypeTemplateCheck typeTemplateCheck) {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        typeTemplateCheck.setSellerId(name);
//        return typeCheckService.search(page,rows,typeTemplateCheck);
        return search(page, rows, typeTemplateCheck);
    }

    @RequestMapping("add")
    public Result add(@RequestBody TypeTemplateCheck typeTemplate) {
        Result result = null;
        try {
            typeCheckService.add(typeTemplate);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "操作失败");
        }
        return result;
    }

    @RequestMapping("update")
    public Result update(@RequestBody TypeTemplateCheck typeTemplate) {
        Result result = null;
        try {
            typeCheckService.update(typeTemplate);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "操作失败");
        }
        return result;
    }

    @RequestMapping("findOne")
    public TypeTemplateCheck findOne(Long id) {
        return typeCheckService.findOne(id);
    }

    @RequestMapping("delete")
    public Result delete(Long[] ids) {
        Result result = null;
        try {
            typeCheckService.delete(ids);
            result = new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "操作失败");
        }
        return result;
    }

    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList() {
        return typeCheckService.selectOptionList();
    }

    @RequestMapping("findBySpecList")
    public List<Map> findBySpecList(Long id) {
        return typeCheckService.findBySpecList(id);
    }
}

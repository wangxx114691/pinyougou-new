package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.ContentService;
import com.itheima.core.pojo.ad.Content;
import com.itheima.core.pojo.good.Brand;
import com.itheima.core.pojo.item.ItemCat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("content")
public class ContentController {
    @Reference
    private ContentService contentService;
    @RequestMapping("findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }

    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(){
        return  contentService.findByParentId();

    }

    @RequestMapping("/findBrandName")
    public List<Brand> findBrandName(){
        return contentService.findBrandName();
    }

}

package com.itheima.core.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.ItemSearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("itemsearch")
public class ItemSearchController {

    @Reference
    private ItemSearchService itemSearchService;
    @RequestMapping("search")
    public Map<String,Object> search(@RequestBody Map<String,String> searchMap) {
        return itemSearchService.search(searchMap);
    }
}

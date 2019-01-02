package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("login")
public class LoginController {
    @Reference
    private UserService userService;
    @RequestMapping("name")
    public Map<String,String> showName(){
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> map = new HashMap();
        map.put("loginName",name);
        userService.addUserCount(name);
        return map;
    }
}

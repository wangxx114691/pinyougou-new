package com.itheima.core.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 用于运营商后台查询用户数
     * @param request
     * @return
     */
    @RequestMapping("searchCount")
    @CrossOrigin(origins = "http://localhost:9101")
    public Map<String,Long> searchCount(HttpServletRequest request){
        Map<String,Long> map = new HashMap<>();
        map.put("totalUserP", (Long)request.getSession().getServletContext().getAttribute("totalUserP"));
        return map;
    }
}

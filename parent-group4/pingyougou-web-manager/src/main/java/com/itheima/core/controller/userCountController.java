package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.UserService;
import com.itheima.core.pojo.user.User;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class userCountController {

    @Reference
    private UserService userService;

    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping("search")
    public PageResult<User> search(Integer page, Integer rows, @RequestBody(required = false) User user){
        return userService.search(page,rows,user);
    }

    @RequestMapping("pointsStatus")
    public Result pointsStatus(Long[] ids){
        try {
            userService.pointsStatus(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("downdele")
    public Result downdele(Long[] ids){
        try {
            userService.downdele(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }
}

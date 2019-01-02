package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.UserService;
import com.itheima.core.pojo.user.User;
import com.itheima.core.pojo.user.User2;
import com.itheima.core.utils.PhoneFormatCheckUtils;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojogroup.UserVo;

import java.util.regex.PatternSyntaxException;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;
    @RequestMapping("sendCode")
    public Result sendCode(String phone){
        // 校验手机号
        try {
            if (PhoneFormatCheckUtils.isPhoneLegal(phone)){
                // 合法则接着比较验证码
                userService.sendCode(phone);

                return new Result(true,"成功");
            }else {// 不合法,直接返回手机号不对
                return new Result(false,"手机号不合法");
            }
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            return new Result(false,"验证码校验失败");
        }
    }

    @RequestMapping("add")
    public Result add(@RequestBody User user,String smscode){
        // 校验手机号
        try {
            userService.add(user,smscode);//smscode 204925
            return new Result(true,"注册成功");
        } catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"注册失败");
        }
    }
<<<<<<< HEAD

    /**
     * 查询登录后的用户数
     * @return
     */
    @RequestMapping("searchCount")
    @CrossOrigin(origins = "http://localhost:9101")
    public Map<String,Long> searchCount(){
        Map<String,Long> map = new HashMap<>();
        Long size = userService.searchCountU();
        map.put("totalUserU",size);
        return map;
    }

    /**
     * 查询活跃度
     * @return
     */
    @RequestMapping("searchActive")
    @CrossOrigin(origins = "http://localhost:9101")
    public Map<String,Integer> searchActive(){
        return userService.searchActive();
    }

    @RequestMapping("/save")
    public UserVo save(@RequestBody User2 user2, @RequestBody User user) {
        return userService.save(user2, user);
    }

=======
>>>>>>> parent of 2babc78... 王欣欣提交
}

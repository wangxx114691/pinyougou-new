package com.itheima.core;

import com.itheima.core.pojo.user.User;
import com.itheima.core.pojo.user.User2;
import entity.PageResult;
import pojogroup.UserVo;

import java.util.Map;

public interface UserService {
    void sendCode(String phone);

    void add(User user, String smscode);

    Map<String,String> searchCount();

    Long searchCountU();

    void addUserCount(String name);

    Map<String,Integer> searchActive();

    PageResult<User> search(Integer page, Integer rows, User user);

    void pointsStatus(Long[] ids);

    void downdele(Long[] ids);
    UserVo save(User2 user2, User user) ;
}

package com.itheima.core;

import com.itheima.core.pojo.user.User;
import com.itheima.core.pojo.user.User2;
import pojogroup.UserVo;

public interface UserService {
    void sendCode(String phone);

    void add(User user, String smscode);

    UserVo save(User2 user2, User user);
}

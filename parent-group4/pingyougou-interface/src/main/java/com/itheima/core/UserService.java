package com.itheima.core;

import com.itheima.core.pojo.user.User;

public interface UserService {
    void sendCode(String phone);

    void add(User user, String smscode);
}

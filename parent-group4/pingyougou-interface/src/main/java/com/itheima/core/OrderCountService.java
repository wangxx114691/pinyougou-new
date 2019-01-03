package com.itheima.core;

import entity.OrdersVo;

public interface OrderCountService {
    OrdersVo search(Integer page, Integer rows, OrdersVo ordersVo, String name);
}

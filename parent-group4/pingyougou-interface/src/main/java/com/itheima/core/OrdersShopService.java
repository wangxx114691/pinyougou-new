package com.itheima.core;

import com.itheima.core.pojo.order.Order;
import entity.PageResult;

public interface OrdersShopService {

    PageResult<Order> search(Integer page, Integer rows, Order order);

}

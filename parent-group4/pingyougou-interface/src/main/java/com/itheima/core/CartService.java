package com.itheima.core;

import com.itheima.core.pojo.Cart;
import com.itheima.core.pojo.item.Item;

import java.util.List;

public interface CartService {
    Item findItemById(Long itemId);

    List<Cart> merge(List<Cart> cartList,String name);


    List<Cart> findCartListFromRedis(String name);

    List<Cart> findCartList(List<Cart> cartList);
}

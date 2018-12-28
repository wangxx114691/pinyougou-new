package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.CartService;
import com.itheima.core.dao.item.ItemDao;
import com.itheima.core.pojo.Cart;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Item findItemById(Long itemId) {
        return itemDao.selectByPrimaryKey(itemId);
    }

    @Override
    public List<Cart> findCartListFromRedis(String name) {
        return (List<Cart>) redisTemplate.boundHashOps("CART").get(name);
    }

    @Override
    public List<Cart> findCartList(List<Cart> cartList) {
        for (Cart cart : cartList) {
            List<OrderItem> orderItemList = cart.getOrderItemList();
            Item item = null;
            for (OrderItem orderItem : orderItemList) {
                item = findItemById(orderItem.getItemId());
                orderItem.setPicPath(item.getImage());
                orderItem.setTitle(item.getTitle());
                orderItem.setPrice(item.getPrice());
                orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * orderItem.getNum()));
            }
            cart.setSellerName(item.getSeller());
        }
        return cartList;
    }

    @Override
    public List<Cart> merge(List<Cart> newCartList, String name) {
        // 传name的原因是需要区分不同商家对应的购物车
        // 获取旧的购物车
        List<Cart> oldCartList = (List<Cart>) redisTemplate.boundHashOps("CART").get(name);
        // 将两大购物车进行合并
        oldCartList = merge1(newCartList, oldCartList);
        // 将合并后的车重新保存到缓存中
        redisTemplate.boundHashOps("CART").put(name,oldCartList);
        return oldCartList;
    }

    public List<Cart> merge1(List<Cart> newCartList, List<Cart> oldCartList) {

        if (null != newCartList && newCartList.size() > 0) {
            // 新购物车不为空
            if (null != oldCartList && oldCartList.size() > 0) {
                // 旧购物车不为空 将新购物车挨个添加到就购物车中
                // 挨个添加又需要挨个比较是否有相同项
                for (Cart newCart : newCartList) {
                    int index = oldCartList.indexOf(newCart);  // 使用这indexOf底层走的是equals方法, 所以必须将equals和hashCode方法进行重写
                    if (index != -1) {// -1表示没有
                        // 原来购物车集合已经有这购物车
                        Cart oldCart = oldCartList.get(index);
                        List<OrderItem> oldOrderItemList = oldCart.getOrderItemList();
                        List<OrderItem> newOrderItemList = newCart.getOrderItemList();
                        if (null != newOrderItemList && newOrderItemList.size() > 0) {
                            for (OrderItem newOrderItem : newOrderItemList) {
                                int orderIndex = oldOrderItemList.indexOf(newOrderItem);
                                if (orderIndex != -1) {
                                    OrderItem oldOrderItem = oldOrderItemList.get(orderIndex);
                                    oldOrderItem.setNum(oldOrderItem.getNum() + newOrderItem.getNum());
                                } else {
                                    oldOrderItemList.add(newOrderItem);
                                }
                            }
                        }
                    } else {
                        // -不存在 添加新购物车
                        oldCartList.add(newCart);
                    }
                }
            } else {
                // 新不为空, 旧为空
                return newCartList;
            }
        }
        // 新购物车为空
        return oldCartList;
    }
}

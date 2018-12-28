package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.core.CartService;
import com.itheima.core.pojo.Cart;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.order.OrderItem;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Reference
    private CartService cartService;

    @RequestMapping("addGoodsToCartList")
//    @CrossOrigin(origins = "http://localhost:9003",allowCredentials = "true")
    @CrossOrigin(origins = "http://localhost:9003")
    public Result addGoodsToCartList(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Cart> cartList = null;
            //Cookie中是否有购物车
            boolean flag = false;
//            1:获取Cookie 数组
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("CART".equals(cookie.getName())) {
                        // 2:获取Cookie中的购物车
                        cartList = JSON.parseArray(cookie.getValue(), Cart.class);
                        //这个方法记清楚了,自动返回的就是List, 但是必须制定泛型类型
                        // 如果不指定反省类型, 则返回的就是JSONArray, 所以一定记得指定泛型
                        flag = true;
                        break;
                    }
                }
            }
//            3:没有 创建购物车对象
            if (null == cartList) {
                cartList = new ArrayList<Cart>();
            }
//            4:追加当前款
            Cart newCart = new Cart();
            Item item = cartService.findItemById(itemId);
            newCart.setSellerId(item.getSellerId());
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setItemId(itemId);
            newOrderItem.setNum(num);
//            newOrderItem.setTitle(item.getTitle());  //这些可以注掉, 不是关键信息所以无足轻重
//            newOrderItem.setPicPath(item.getImage());
//            newOrderItem.setPrice(item.getPrice());
//            newOrderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
            List<OrderItem> newOrderItemList = new ArrayList<>();
            newOrderItemList.add(newOrderItem);
            newCart.setOrderItemList(newOrderItemList);
            // 接下来添加购物车需要判断是直接添加还是只是数量的改变
            int index = cartList.indexOf(newCart);  // 使用这indexOf底层走的是equals方法, 所以必须将equals和hashCode方法进行重写
            if (index != -1) {// -1表示没有
                // 原来购物车集合已经有这购物车
                Cart oldCart = cartList.get(index);
                List<OrderItem> oldOrderItemList = oldCart.getOrderItemList();
                int orderIndex = oldOrderItemList.indexOf(newOrderItem);
                if (orderIndex != -1) {
                    OrderItem oldOrderItem = oldOrderItemList.get(orderIndex);
                    oldOrderItem.setNum(oldOrderItem.getNum() + num);
                } else {
                    oldOrderItemList.add(newOrderItem);
                }
            } else {
                cartList.add(newCart);
            }
            // 判断用户是否登录
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
//            Cookie cookie = new Cookie("CART", JSON.toJSONString(newCart));
//            cookie.setPath("/"); // 必须设置这个不然会默认设置为"/cart"
            if (!"anonymousUser".equals(name)) {    // 这里使用匿名用户, 原因是即使是匿名用户也会给其服务器也要给其做响应的记录, 如果没有绑定的用户的话, 就会造成记录不了用户信息
                // 已登录
//                5.将当前购物车合并到原来购物车中
                cartService.merge(cartList, name);
//                6.清空cookie    // 清空之间先做判断是否已经取出来了, 否则提交个错的也会把之前的cookie全删掉
                if (flag) {
                    Cookie cookie = new Cookie("CART",null);
                    cookie.setPath("/"); // 必须设置这个不然会默认设置为"/cart"
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            } else {
                // 未登录
//            1:获取Cookie 数组
//            2:获取Cookie中的购物车
//            3:没有 创建购物车对象
//            4:追加当前款
//            5:将当前购物车 保存到Cookie
                Cookie cookie = new Cookie("CART", JSON.toJSONString(cartList));
                cookie.setMaxAge(Integer.MAX_VALUE);
                cookie.setPath("/"); // 必须设置这个不然会默认设置为"/cart"
//            6:回写到浏览器中
                response.addCookie(cookie);
            }


            return new Result(true, "加入购物车成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "加入购物车失败");
        }
    }

    // 查询购物车进行回显
    @RequestMapping("findCartList")
    public List<Cart> findCartList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Cart> cartList = null;
//            1:获取Cookie 数组
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("CART".equals(cookie.getName())) {
                        // 2:获取Cookie中的购物车
                        cartList = JSON.parseArray(cookie.getValue(), Cart.class);
                        //这个方法记清楚了,自动返回的就是List, 但是必须制定泛型类型
                        // 如果不指定反省类型, 则返回的就是JSONArray, 所以一定记得指定泛型
                        break;
                    }
                }
            }
            // 判断是否登录
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!"anonymousUser".equals(name)) {    // 这里使用匿名用户, 原因是即使是匿名用户也会给其服务器也要给其做响应的记录, 如果没有绑定的用户的话, 就会造成记录不了用户信息
                // 已登录
//                3.将当前购物车合并到原来购物车中  清空cookie
                if (null != cartList) {
                    cartService.merge(cartList, name);
                    Cookie cookie = new Cookie("CART", null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
//                4:将帐户中购物车取出来
                cartList = cartService.findCartListFromRedis(name);

            }
            // 未登录
            //5.cookie中有 将购物车结果集装满 // 还需判断购物车是否为空
            // 装满的意思就是,从cookie中取出的缩减版购物车结果集, 将数据全部填满, 然后再返回给页面
            if (null != cartList) {
                cartList = cartService.findCartList(cartList);
            }
            //6.回显 List<Cart> //若list为空,就相当于返回null
            return cartList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

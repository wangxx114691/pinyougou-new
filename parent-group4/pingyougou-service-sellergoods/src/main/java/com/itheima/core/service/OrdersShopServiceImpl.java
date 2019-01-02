package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.OrdersShopService;
import com.itheima.core.dao.order.OrderDao;
import com.itheima.core.dao.order.OrderItemDao;
import com.itheima.core.pojo.order.Order;
import com.itheima.core.pojo.order.OrderItem;
import com.itheima.core.pojo.order.OrderItemQuery;
import com.itheima.core.pojo.order.OrderQuery;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrdersShopServiceImpl implements OrdersShopService {


    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResult<Order> search(Integer page, Integer rows, Order order) {
        PageHelper.startPage(page,rows);
        OrderQuery orderQuery=new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        if (order.getOrderId()!=null&& !"".equals(String.valueOf(order.getOrderId()).trim())){
            criteria.andOrderIdEqualTo(order.getOrderId());
        }
        criteria.andSellerIdEqualTo(order.getSellerId());
        Page<Order> p = (Page<Order>) orderDao.selectByExample(orderQuery);
        return new PageResult(p.getTotal(),p.getResult());
    }

}

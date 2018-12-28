package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.OrderService;
import com.itheima.core.dao.item.ItemDao;
import com.itheima.core.dao.log.PayLogDao;
import com.itheima.core.dao.order.OrderDao;
import com.itheima.core.dao.order.OrderItemDao;
import com.itheima.core.pojo.Cart;
import com.itheima.core.pojo.item.Item;
import com.itheima.core.pojo.log.PayLog;
import com.itheima.core.pojo.order.Order;
import com.itheima.core.pojo.order.OrderItem;
import com.itheima.core.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private PayLogDao payLogDao;
    @Autowired
    private IdWorker idWorker;
    @Override
    public void add(Order order) {
        double totalPay = 0;
        List<String> ids = new ArrayList<>();
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART").get(order.getUserId());
        for (Cart cart : cartList) {

            double totalPrice = 0;
            // 封装order
            order.setSellerId(cart.getSellerId());
            long id = idWorker.nextId();
            ids.add(String.valueOf(id));
            order.setOrderId(id);
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            order.setPaymentType("1");
            order.setStatus("1");
            order.setSourceType("2");
            List<OrderItem> orderItemList = cart.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                // 接下来封装orderItem,并保存到数据库表中
                orderItem.setId(idWorker.nextId());
                orderItem.setOrderId(order.getOrderId());
                Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());
                orderItem.setGoodsId(item.getGoodsId());
                orderItem.setTitle(item.getTitle());
                orderItem.setPrice(item.getPrice());
                orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*orderItem.getNum()));
                orderItem.setSellerId(order.getSellerId());
                orderItem.setPicPath(orderItem.getPicPath());
                totalPrice +=orderItem.getTotalFee().doubleValue();

                orderItemDao.insertSelective(orderItem);
            }

            order.setPayment(new BigDecimal(totalPrice));
            totalPay += totalPrice;
            orderDao.insertSelective(order);
        }
        // 删除缓存中的相应的订单
        redisTemplate.boundHashOps("CART").delete(order.getUserId());

        // 将两张订单合并, 并生成银行流水, 支付日志表
        PayLog payLog = new PayLog();
        payLog.setCreateTime(new Date());
        payLog.setUserId(order.getUserId());
        payLog.setTradeState("0");
        payLog.setTotalFee((long)totalPay*100);
        payLog.setOutTradeNo(String.valueOf(order.getOrderId()));
        // 订单集合, 比较特别
        payLog.setOrderList(ids.toString().replace("[","").replace("]",""));
        payLogDao.insertSelective(payLog);
        redisTemplate.boundHashOps("payLog").put(order.getUserId(),payLog); // 最后一步放在缓存中
    }
}

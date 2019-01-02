package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.OrderCountService;
import com.itheima.core.dao.order.OrderDao;
import com.itheima.core.dao.order.OrderItemDao;
import com.itheima.core.pojo.order.Order;
import com.itheima.core.pojo.order.OrderQuery;
import entity.OrdersVo;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderCountServiceImpl implements OrderCountService {


    @Autowired
    private OrderDao orderDao;


    @Override
    public OrdersVo search(Integer page,Integer rows, OrdersVo ordersVo,String name) {

        PageHelper.startPage(page,rows);
        //创建包装类对象 给包装类里面填值
        OrdersVo ordersVoValue =new OrdersVo();
        //创建订单表的查询对象
        OrderQuery orderQuery=new OrderQuery();

        //创建表的查询条件
        OrderQuery.Criteria criteria = orderQuery.createCriteria();

       if (ordersVo.getBeginTime()!=null&&ordersVo.getOverTime()!=null){
           criteria.andCreateTimeBetween(ordersVo.getBeginTime(),ordersVo.getOverTime());
       }
        criteria.andSellerIdEqualTo(name);
       //查询商家的所有订单集合
        Page<Order> orders = (Page<Order>) orderDao.selectByExample(orderQuery);

        PageResult pageResult = new PageResult(orders.getTotal(), orders.getResult());
        ordersVoValue.setPageResult(pageResult);

        //查询已经付款的订单 状态为2
         criteria.andStatusEqualTo("2");
        List<Order> orderList = orderDao.selectByExample(orderQuery);
        //设置成交量
        ordersVoValue.setOrderNum(orderList.size());

        BigDecimal totalPrice=new BigDecimal(0);
        for (Order order : orderList) {
            totalPrice=totalPrice.add(order.getPayment());
        }
        //设置销售总金额
        ordersVoValue.setOrderTotalPrice(totalPrice);
        return ordersVoValue;
    }
    public String dateToString(Date time){
        if (time==null){
            Calendar date=Calendar.getInstance();
            date.set(1970,0,1);
            time=date.getTime();
        }
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(time);
        return format;
    }
    public String dateToString2(Date time){
        if (time==null){
            time=new Date();
        }
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(time);
        return format;
    }

    public Date StringtoDate(String time) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}

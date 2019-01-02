package entity;

import com.github.pagehelper.Page;
import com.itheima.core.pojo.order.Order;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrdersVo implements Serializable{

    //订单集合
    private Page<Order> orderList;
    private PageResult pageResult;
    //订单数量
    private Integer orderTotal;
    //订单成交量
    private Integer orderNum;
    //订单总金额
    private BigDecimal orderTotalPrice;

    //订单创建时间的结束时间
    //订单创建时间的开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date overTime;


    public Page<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(Page<Order> orderList) {
        this.orderList = orderList;
    }

    public PageResult getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
}

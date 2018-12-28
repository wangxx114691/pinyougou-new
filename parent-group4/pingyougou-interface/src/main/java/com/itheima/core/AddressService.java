package com.itheima.core;

import com.itheima.core.pojo.address.Address;
import com.itheima.core.pojo.order.Order;

import java.util.List;

public interface AddressService {
    List<Address> findListByLoginUser(String name);
}

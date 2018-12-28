package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.AddressService;
import com.itheima.core.dao.address.AddressDao;
import com.itheima.core.pojo.address.Address;
import com.itheima.core.pojo.address.AddressQuery;
import com.itheima.core.pojo.order.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findListByLoginUser(String name) {
        AddressQuery query = new AddressQuery();
        query.createCriteria().andUserIdEqualTo(name);
        return addressDao.selectByExample(query);
    }
}

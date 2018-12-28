package com.itheima.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.AddressService;
import com.itheima.core.pojo.address.Address;
import com.itheima.core.pojo.order.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    @Reference
    private AddressService addressService;
    @RequestMapping("findListByLoginUser")
    public List<Address> findListByLoginUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByLoginUser(name);
    }
}

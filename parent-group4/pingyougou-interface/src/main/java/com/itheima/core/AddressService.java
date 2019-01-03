package com.itheima.core;

import com.itheima.core.pojo.address.Address;
import com.itheima.core.pojo.address.Areas;
import com.itheima.core.pojo.address.Cities;
import com.itheima.core.pojo.address.Provinces;
import com.itheima.core.pojo.order.Order;
import pojogroup.AddressVo;

import java.util.List;

public interface AddressService {
    List<Address> findListByLoginUser(String name);

    List<AddressVo> findAll(String name);

    List<Provinces> findProvinceList();

    List<Cities> findByProvinceId(String provinceid);

    List<Areas> findByCityId(String cityid);

    AddressVo findOne(Long id);

    void add(AddressVo address);

    void update(AddressVo address);

    void delete(Long id);

    void setIsDefault(Long id);
}

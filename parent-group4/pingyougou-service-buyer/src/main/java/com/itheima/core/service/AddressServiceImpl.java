package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.core.AddressService;
import com.itheima.core.dao.address.AddressDao;
import com.itheima.core.dao.address.AreasDao;
import com.itheima.core.dao.address.CitiesDao;
import com.itheima.core.dao.address.ProvincesDao;
import com.itheima.core.pojo.address.*;
import org.springframework.beans.factory.annotation.Autowired;
import pojogroup.AddressVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private ProvincesDao provincesDao;
    @Autowired
    private CitiesDao citiesDao;
    @Autowired
    private AreasDao areasDao;


    @Override
    public List<Address> findListByLoginUser(String name) {
        AddressQuery query = new AddressQuery();
        query.createCriteria().andUserIdEqualTo(name);
        return addressDao.selectByExample(query);
    }

    @Override
    public List<AddressVo> findAll(String name) {
        List<AddressVo> addressVoList = new ArrayList<>();
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
        List<Address> addressList = addressDao.selectByExample(addressQuery);
        for (Address address : addressList) {
            AddressVo addressVo = new AddressVo();
            // id
            addressVo.setId(address.getId());
            // 用户ID
            addressVo.setUserId(address.getUserId());
            // 省
            if (address.getProvinceId() != null && !"".equals(address.getProvinceId())){
                ProvincesQuery provincesQuery = new ProvincesQuery();
                provincesQuery.createCriteria().andProvinceidEqualTo(address.getProvinceId());
                List<Provinces> provinces = provincesDao.selectByExample(provincesQuery);
                Provinces province = provinces.get(0);
                addressVo.setProvince(province);
            }
            // 市
            if (address.getCityId() != null && !"".equals(address.getCityId())) {
                CitiesQuery citiesQuery = new CitiesQuery();
                citiesQuery.createCriteria().andCityidEqualTo(address.getCityId());
                List<Cities> cities = citiesDao.selectByExample(citiesQuery);
                Cities city = cities.get(0);
                addressVo.setCity(city);
            }
            // 县/区
            if (address.getTownId() != null && !"".equals(address.getTownId())) {
                AreasQuery areasQuery = new AreasQuery();
                areasQuery.createCriteria().andAreaidEqualTo(address.getTownId());
                List<Areas> areas = areasDao.selectByExample(areasQuery);
                Areas area = areas.get(0);
                addressVo.setArea(area);
            }
            // 手机
            addressVo.setMobile(address.getMobile());
            // 详细地址
            addressVo.setAddress(address.getAddress());
            // 联系人
            addressVo.setContact(address.getContact());
            // 是否是默认 1默认 0否
            addressVo.setIsDefault(address.getIsDefault());
            // 备注
            addressVo.setNotes(address.getNotes());
            // 创建日期
            addressVo.setCreateDate(address.getCreateDate());
            // 别名
            addressVo.setAlias(address.getAlias());
            addressVoList.add(addressVo);
        }
        return addressVoList;
    }

    @Override
    public List<Provinces> findProvinceList() {
        List<Provinces> provincesList = provincesDao.selectByExample(null);
        return provincesList;
    }

    @Override
    public List<Cities> findByProvinceId(String provinceid) {
        CitiesQuery citiesQuery = new CitiesQuery();
        citiesQuery.createCriteria().andProvinceidEqualTo(provinceid);
        List<Cities> citiesList = citiesDao.selectByExample(citiesQuery);
        return citiesList;
    }

    @Override
    public List<Areas> findByCityId(String cityid) {
        AreasQuery areasQuery = new AreasQuery();
        areasQuery.createCriteria().andCityidEqualTo(cityid);
        List<Areas> areasList = areasDao.selectByExample(areasQuery);
        return areasList;
    }

    @Override
    public AddressVo findOne(Long id) {
        Address address = addressDao.selectByPrimaryKey(id);
        AddressVo addressVo = new AddressVo();
        // id
        addressVo.setId(id);
        // 用户ID
        addressVo.setUserId(address.getUserId());
        // 省
        if (address.getProvinceId() != null && !"".equals(address.getProvinceId())){
            ProvincesQuery provincesQuery = new ProvincesQuery();
            provincesQuery.createCriteria().andProvinceidEqualTo(address.getProvinceId());
            List<Provinces> provinces = provincesDao.selectByExample(provincesQuery);
            Provinces province = provinces.get(0);
            addressVo.setProvince(province);
        }
        // 市
        if (address.getCityId() != null && !"".equals(address.getCityId())) {
            CitiesQuery citiesQuery = new CitiesQuery();
            citiesQuery.createCriteria().andCityidEqualTo(address.getCityId());
            List<Cities> cities = citiesDao.selectByExample(citiesQuery);
            Cities city = cities.get(0);
            addressVo.setCity(city);
        }
        // 县/区
        if (address.getTownId() != null && !"".equals(address.getTownId())) {
            AreasQuery areasQuery = new AreasQuery();
            areasQuery.createCriteria().andAreaidEqualTo(address.getTownId());
            List<Areas> areas = areasDao.selectByExample(areasQuery);
            Areas area = areas.get(0);
            addressVo.setArea(area);
        }
        // 手机
        addressVo.setMobile(address.getMobile());
        // 详细地址
        addressVo.setAddress(address.getAddress());
        // 联系人
        addressVo.setContact(address.getContact());
        // 是否是默认 1默认 0否
        addressVo.setIsDefault(address.getIsDefault());
        // 备注
        addressVo.setNotes(address.getNotes());
        // 创建日期
        addressVo.setCreateDate(address.getCreateDate());
        // 别名
        addressVo.setAlias(address.getAlias());
//        return addressDao.selectByPrimaryKey(id);
        return addressVo;
    }

    @Override
    public void add(AddressVo address) {
        Address address1 = new Address();
        address1.setUserId(address.getUserId());
        address1.setProvinceId(address.getProvince().getProvinceid());
        address1.setCityId(address.getCity().getCityid());
        address1.setTownId(address.getArea().getAreaid());
        address1.setMobile(address.getMobile());
        address1.setAddress(address.getAddress());
        address1.setContact(address.getContact());
        address1.setIsDefault("0");
        address1.setNotes(address.getNotes());
        address1.setCreateDate(new Date());
        address1.setAlias(address.getAlias());
        addressDao.insertSelective(address1);
    }

    @Override
    public void update(AddressVo address) {
        Address address1 = new Address();
        address1.setId(address.getId());
        address1.setUserId(address.getUserId());
        address1.setProvinceId(address.getProvince().getProvinceid());
        address1.setCityId(address.getCity().getCityid());
        address1.setTownId(address.getArea().getAreaid());
        address1.setMobile(address.getMobile());
        address1.setAddress(address.getAddress());
        address1.setContact(address.getContact());
        address1.setIsDefault(address.getIsDefault());
        address1.setNotes(address.getNotes());
        address1.setCreateDate(address.getCreateDate());
        address1.setAlias(address.getAlias());
        addressDao.updateByPrimaryKeySelective(address1);
    }

    @Override
    public void delete(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }

    @Override
    public void setIsDefault(Long id) {
        Address address = addressDao.selectByPrimaryKey(id);
        String userId = address.getUserId();
        // 获取当前用户地址中的默认地址
        AddressQuery addressQuery = new AddressQuery();
        AddressQuery.Criteria criteria = addressQuery.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsDefaultEqualTo("1");
        List<Address> addressList = addressDao.selectByExample(addressQuery);
        if (addressList != null && addressList.size() > 0) {
            Address addr = addressList.get(0);
            // 把默认地址改为非默认
            addr.setIsDefault("0");
            addressDao.updateByPrimaryKeySelective(addr);
        }
        // 设置新的默认地址
        address.setIsDefault("1");
        addressDao.updateByPrimaryKeySelective(address);
    }
}

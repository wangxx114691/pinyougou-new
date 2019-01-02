package com.itheima.core.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.core.AddressService;
import com.itheima.core.pojo.address.Areas;
import com.itheima.core.pojo.address.Cities;
import com.itheima.core.pojo.address.Provinces;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojogroup.AddressVo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/name")
    public Map<String,Object> name(){
        Map<String,Object> map = new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username",name);
        return map;
    }

    /**
     * 根据当前登录用户，进行用户地址的查找
     */
    @RequestMapping("/findAll")
    public List<AddressVo> findAll(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findAll(name);
    }

    // 查询省列表
    @RequestMapping("findProvinceList")
    public List<Provinces> findProvinceList() {
        return addressService.findProvinceList();
    }

    // 根据provinceId查询市列表
    @RequestMapping("findByProvinceId")
    public List<Cities> findByProvinceId(String provinceid) {
        return addressService.findByProvinceId(provinceid);
    }

    // 根据cityId查询区列表
    @RequestMapping("findByCityId")
    public List<Areas> findByCityId(String cityid) {
        return addressService.findByCityId(cityid);
    }

    // 查询一个
    @RequestMapping("findOne")
    public AddressVo findOne(Long id) {
        return addressService.findOne(id);
    }

    // 保存
    @RequestMapping("add")
    public Result add(@RequestBody AddressVo address) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        address.setUserId(name);
        try {
            addressService.add(address);
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }
    }

    // 修改
    @RequestMapping("update")
    public Result update(@RequestBody AddressVo address) {
        try {
            addressService.update(address);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    // 删除
    @RequestMapping("delete")
    public Result delete(Long id){
        try {
            addressService.delete(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    // 设为默认
    @RequestMapping("setIsDefault")
    public Result setIsDefault(Long id){
        try {
            addressService.setIsDefault(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }
}
